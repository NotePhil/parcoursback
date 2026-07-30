package cmr.notep.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cmr.notep.dao.FamillesEntity;
import cmr.notep.dao.PrecoMouvementsEntity;
import cmr.notep.dao.PrecoMouvementsQtesEntity;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.repository.FamillesRepository;
import cmr.notep.repository.PrecoMouvementsQtesRepository;
import cmr.notep.repository.PrecoMouvementsRepository;
import cmr.notep.repository.RessourcesRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cmr.notep.dao.DaoAccessorService;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@Transactional
public class PrecomouvementsBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;

    public PrecomouvementsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService ;
        this.dozerMapperBean = dozerMapperBean;
    }

    public List <PrecoMouvements> avoirTousPrecouvement()
    {
        return daoAccessorService.getRepository(PrecoMouvementsRepository.class).findAll()
                .stream().map(precomouvement -> dozerMapperBean.map(precomouvement,PrecoMouvements.class))
                .collect(Collectors.toList());
    }

    public PrecoMouvements avoirPrecomouvements(String idPreco)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(PrecoMouvementsRepository.class).findById(idPreco)
                .orElseThrow(()->new RuntimeException("Missions non enregistré")), PrecoMouvements.class);
    }

    public PrecoMouvements posterPrecomouvement(PrecoMouvements preco) throws ParcoursException {
        PrecoMouvementsEntity precoEntity;

        if (preco.getId() != null) {
            precoEntity = this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                    .findById(preco.getId())
                    .orElseThrow(() -> new RuntimeException("PrecoMouvement non trouvé : " + preco.getId()));
            dozerMapperBean.map(preco, precoEntity);
        } else {
            precoEntity = dozerMapperBean.map(preco, PrecoMouvementsEntity.class);
        }

        // Le précomouvement doit être persisté (et donc disposer d'un id) avant de
        // traiter ses quantités, car celles-ci référencent precomouvements_id (NOT NULL).
        precoEntity = this.daoAccessorService.getRepository(PrecoMouvementsRepository.class).save(precoEntity);

        gererPrecoMouvementsQtes(preco, precoEntity);

        return dozerMapperBean.map(precoEntity, PrecoMouvements.class);
    }

    private void gererPrecoMouvementsQtes(PrecoMouvements preco, PrecoMouvementsEntity precoEntity) throws ParcoursException {
        List<PrecoMouvementsQtes> qtesList = preco.getPrecoMouvementsQtes();
        PrecoMouvementsQtesRepository qtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);
        FamillesRepository famillesRepository = daoAccessorService.getRepository(FamillesRepository.class);
        RessourcesRepository ressourcesRepository = daoAccessorService.getRepository(RessourcesRepository.class);

        List<PrecoMouvementsQtesEntity> existingQtes = precoEntity.getPrecoMouvementsQteEntities() != null
                ? new ArrayList<>(precoEntity.getPrecoMouvementsQteEntities())
                : new ArrayList<>();

        // Si liste absente ou vide => purger toutes les quantités
        if (CollectionUtils.isEmpty(qtesList)) {
            try {
                for (PrecoMouvementsQtesEntity existing : existingQtes) {
                    qtesRepo.delete(existing);
                }
                if (precoEntity.getPrecoMouvementsQteEntities() != null) {
                    precoEntity.getPrecoMouvementsQteEntities().clear();
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les quantités du précomouvement: " + e.getMessage(), e);
            }
            return;
        }

        List<String> newIds = qtesList.stream()
                .filter(q -> q.getId() != null)
                .map(PrecoMouvementsQtes::getId)
                .toList();

        // Supprimer les anciens non présents dans la nouvelle liste
        for (PrecoMouvementsQtesEntity existing : existingQtes) {
            if (!newIds.contains(existing.getId())) {
                try {
                    qtesRepo.delete(existing);
                    precoEntity.getPrecoMouvementsQteEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer la quantité " + existing.getId() + ": " + e.getMessage(), e);
                }
            }
        }

        if (precoEntity.getPrecoMouvementsQteEntities() == null) {
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
        }

        // Ajouter ou mettre à jour
        for (PrecoMouvementsQtes qte : qtesList) {
            PrecoMouvementsQtesEntity qteEntity;
            if (qte.getId() != null) {
                qteEntity = qtesRepo.findById(qte.getId())
                        .orElseThrow(() -> new RuntimeException("PrecoMouvementsQte non trouvée : " + qte.getId()));
                dozerMapperBean.map(qte, qteEntity);
            } else {
                qteEntity = dozerMapperBean.map(qte, PrecoMouvementsQtesEntity.class);
                qteEntity.setId(null);
            }

            // Les références de relations doivent pointer vers des entités persistées.
            if (qte.getRessource() != null) {
                String ressourceId = qte.getRessource().getId();
                if (ressourceId == null) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Ressource invalide: id manquant sur la quantité du précomouvement");
                }
                RessourcesEntity ressourceEntity = ressourcesRepository.findById(ressourceId)
                        .orElseThrow(() -> new RuntimeException("Ressource non trouvée : " + ressourceId));
                qteEntity.setRessourcesEntity(ressourceEntity);
            } else {
                qteEntity.setRessourcesEntity(null);
            }

            qteEntity.setPrecoMouvementsEntity(precoEntity);
            try {
                PrecoMouvementsQtesEntity savedQte = qtesRepo.save(qteEntity);

                // La relation qte<->familles est possédée par FamillesEntity (JoinTable),
                // on synchronise donc explicitement ce côté propriétaire.
                List<FamillesEntity> famillesCibles = new ArrayList<>();
                if (!CollectionUtils.isEmpty(qte.getFamilles())) {
                    for (cmr.notep.modele.Familles famille : qte.getFamilles()) {
                        if (famille.getId() == null) {
                            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                                    "Famille invalide: id manquant sur la quantité du précomouvement");
                        }
                        FamillesEntity familleEntity = famillesRepository.findById(famille.getId())
                                .orElseThrow(() -> new RuntimeException("Famille non trouvée : " + famille.getId()));
                        if (familleEntity.getPrecoMouvementsQtesEntities() == null) {
                            familleEntity.setPrecoMouvementsQtesEntities(new ArrayList<>());
                        }
                        if (!familleEntity.getPrecoMouvementsQtesEntities().contains(savedQte)) {
                            familleEntity.getPrecoMouvementsQtesEntities().add(savedQte);
                        }
                        famillesRepository.save(familleEntity);
                        famillesCibles.add(familleEntity);
                    }
                }

                if (savedQte.getFamillesEntities() == null) {
                    savedQte.setFamillesEntities(new ArrayList<>());
                }
                Set<String> idsFamillesCibles = famillesCibles.stream().map(FamillesEntity::getId).collect(Collectors.toSet());
                for (FamillesEntity familleExistante : new ArrayList<>(savedQte.getFamillesEntities())) {
                    if (!idsFamillesCibles.contains(familleExistante.getId())
                            && familleExistante.getPrecoMouvementsQtesEntities() != null) {
                        familleExistante.getPrecoMouvementsQtesEntities().removeIf(q -> savedQte.getId().equals(q.getId()));
                        famillesRepository.save(familleExistante);
                    }
                }
                savedQte.setFamillesEntities(famillesCibles);

                if (!precoEntity.getPrecoMouvementsQteEntities().contains(savedQte)) {
                    precoEntity.getPrecoMouvementsQteEntities().add(savedQte);
                }
            } catch (ParcoursException e) {
                throw e;
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'enregistrer la quantité du précomouvement: " + e.getMessage(), e);
            }
        }
        precoEntity.getPrecoMouvementsQteEntities().forEach(qte -> {
            if (qte.getPrecoMouvementsEntity() == null) {
                qte.setPrecoMouvementsEntity(precoEntity);
            }
        });
    }

    public void supprimerPrecomouvement(PrecoMouvements precomouvement) {
        daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .deleteById(precomouvement.getId().toString());
    }

    public PrecoMouvements modifierPrecomouvement(PrecoMouvements precomouvement) {
        // Vérifier que le précomouvement existe avant de le modifier
        this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .findById(precomouvement.getId().toString())
                .orElseThrow(() -> new RuntimeException("Précomouvement non enregistré"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                        .save(dozerMapperBean.map(precomouvement, PrecoMouvementsEntity.class)),
                PrecoMouvements.class);
    }
}
