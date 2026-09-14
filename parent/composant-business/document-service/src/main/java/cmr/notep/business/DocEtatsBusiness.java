package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.DocEtatsEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.DocEtats;
import cmr.notep.repository.DocEtatsRepository;
import cmr.notep.repository.DocumentsRepository;
import cmr.notep.repository.EtapesRepository;
import cmr.notep.repository.EtatsRepository;
import cmr.notep.repository.ValidationsRepository;
import cmr.notep.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Business de gestion des {@link DocEtats}, calquée sur {@link CategoriesBusiness}.
 * <p>
 * Règle métier : lors de l'enregistrement d'un document, les occurrences de DocEtats
 * sont créées si leur {@code id} est absent, ou modifiées si leur {@code id} est présent.
 * Les prédécesseurs référencés (predecesseurDocEtat) doivent nécessairement déjà exister
 * en base (ils sont résolus par id, jamais créés à la volée).
 */
@Component
@Slf4j
@Transactional
public class DocEtatsBusiness {
    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;
    private final DocEtatsRepository docEtatsRepository;

    public DocEtatsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean, DocEtatsRepository docEtatsRepository) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
        this.docEtatsRepository = docEtatsRepository;
    }

    public DocEtats avoirDocEtat(String idDocEtat) {
        return dozerMapperBean.map(daoAccessorService.getRepository(DocEtatsRepository.class)
                .findById(idDocEtat)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                        "DocEtat introuvable : " + idDocEtat)), DocEtats.class);
    }

    public List<DocEtats> avoirToutDocEtats() {
        return daoAccessorService.getRepository(DocEtatsRepository.class).findAll()
                .stream().map(docEtat -> dozerMapperBean.map(docEtat, DocEtats.class))
                .collect(Collectors.toList());
    }

    public void supprimerDocEtat(DocEtats docEtat) {
        DocEtatsRepository repo = daoAccessorService.getRepository(DocEtatsRepository.class);
        DocEtatsEntity entity = repo.findById(docEtat.getId())
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                        "DocEtat introuvable : " + docEtat.getId()));
        supprimerDocEtatEntity(entity, repo);
    }

    /**
     * Supprime une occurrence DocEtats déjà chargée, en la retirant au préalable des listes
     * de prédécesseurs des autres DocEtats qui la référencent (pour respecter la contrainte FK).
     */
    public void supprimerDocEtatEntity(DocEtatsEntity entity, DocEtatsRepository repo) {
        try {
            List<DocEtatsEntity> referencants = repo.findAll().stream()
                    .filter(de -> de.getPredecesseursDocEtat() != null && de.getPredecesseursDocEtat().contains(entity))
                    .toList();
            for (DocEtatsEntity referencant : referencants) {
                referencant.getPredecesseursDocEtat().remove(entity);
                repo.save(referencant);
            }
        } catch (Exception e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                    "Impossible de nettoyer les références au DocEtat avant suppression: " + e.getMessage(), e);
        }
        repo.deleteById(entity.getId());
    }

    /**
     * Crée ou met à jour une occurrence de DocEtats.
     * <ul>
     *     <li>{@code id} absent : création d'une nouvelle occurrence.</li>
     *     <li>{@code id} présent : modification de l'occurrence existante.</li>
     * </ul>
     */
    public DocEtats posterDocEtat(DocEtats docEtat) {
        DocEtatsRepository repo = daoAccessorService.getRepository(DocEtatsRepository.class);
        DocEtatsEntity entity;

        // Les prédécesseurs sont gérés séparément (résolution par id sur des entités JPA gérées)
        Set<DocEtats> predecesseursDemandes = docEtat.getPredecesseurDocEtat();
        docEtat.setPredecesseurDocEtat(new HashSet<>());

        if (docEtat.getId() != null) {
            entity = repo.findById(docEtat.getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                            "DocEtat non trouvé : " + docEtat.getId()));
            BeanCopyUtils.copyPropertiesExcludingCollections(docEtat, entity);
        } else {
            entity = new DocEtatsEntity();
            entity.setOrdre(docEtat.getOrdre());
            entity.setDateCreation(docEtat.getDateCreation() != null ? docEtat.getDateCreation() : new java.util.Date());
            entity.setPredecesseursDocEtat(new HashSet<>());
            entity = repo.save(entity);
        }

        // Les relations objet (etat, etape, validation, document) sont résolues explicitement
        // vers des entités JPA gérées, afin d'éviter toute création d'entité transitoire par Dozer.
        mapReferencesIfPresent(docEtat, entity);

        // Restaurer la liste sur le modèle appelant (pas d'effet de bord côté client)
        docEtat.setPredecesseurDocEtat(predecesseursDemandes);

        gererPredecesseursDocEtat(predecesseursDemandes, entity, repo);

        entity = repo.save(entity);
        return dozerMapperBean.map(entity, DocEtats.class);
    }

    private void mapReferencesIfPresent(DocEtats docEtat, DocEtatsEntity entity) {
        if (docEtat.getEtat() != null && docEtat.getEtat().getId() != null) {
            entity.setEtatsEntity(daoAccessorService.getRepository(EtatsRepository.class)
                    .findById(docEtat.getEtat().getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                            "Etat introuvable : " + docEtat.getEtat().getId())));
        }
        if (docEtat.getEtape() != null && docEtat.getEtape().getId() != null) {
            entity.setEtapesEntity(daoAccessorService.getRepository(EtapesRepository.class)
                    .findById(docEtat.getEtape().getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                            "Etape introuvable : " + docEtat.getEtape().getId())));
        }
        if (docEtat.getValidation() != null && docEtat.getValidation().getId() != null) {
            entity.setValidationsEntity(daoAccessorService.getRepository(ValidationsRepository.class)
                    .findById(docEtat.getValidation().getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                            "Validation introuvable : " + docEtat.getValidation().getId())));
        }
        if (docEtat.getDocument() != null && docEtat.getDocument().getIdDocument() != null) {
            entity.setDocumentsEntity(daoAccessorService.getRepository(DocumentsRepository.class)
                    .findById(docEtat.getDocument().getIdDocument())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                            "Document introuvable : " + docEtat.getDocument().getIdDocument())));
        }
    }

    /**
     * Synchronise la liste des prédécesseurs d'un DocEtat.
     * Chaque prédécesseur doit obligatoirement déjà exister en base (id requis, jamais créé à la volée).
     * Une liste vide/absente purge les prédécesseurs existants.
     */
    private void gererPredecesseursDocEtat(Set<DocEtats> predecesseursDemandes, DocEtatsEntity entity, DocEtatsRepository repo) {
        if (CollectionUtils.isEmpty(predecesseursDemandes)) {
            if (entity.getPredecesseursDocEtat() == null) {
                entity.setPredecesseursDocEtat(new HashSet<>());
            } else {
                entity.getPredecesseursDocEtat().clear();
            }
            return;
        }

        Set<DocEtatsEntity> managed = predecesseursDemandes.stream()
                .map(pred -> {
                    if (pred.getId() == null) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un prédécesseur DocEtat doit obligatoirement déjà exister (id requis)");
                    }
                    if (pred.getId().equals(entity.getId())) {
                        throw new ParcoursException(ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                                "Un DocEtat ne peut pas être son propre prédécesseur");
                    }
                    return repo.findById(pred.getId())
                            .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,
                                    "Prédécesseur DocEtat introuvable : " + pred.getId()));
                })
                .collect(Collectors.toSet());

        if (entity.getPredecesseursDocEtat() == null) {
            entity.setPredecesseursDocEtat(new HashSet<>());
        } else {
            entity.getPredecesseursDocEtat().clear();
        }
        entity.getPredecesseursDocEtat().addAll(managed);
    }

    public List<DocEtats> listeDocEtatsParDocument(@NonNull String idDocument) {
        return docEtatsRepository.findByDocumentsEntityId(idDocument).stream()
                .map(docEtatsEntity -> dozerMapperBean
                        .map(docEtatsEntity, DocEtats.class)).collect(Collectors.toList());
    }

}
