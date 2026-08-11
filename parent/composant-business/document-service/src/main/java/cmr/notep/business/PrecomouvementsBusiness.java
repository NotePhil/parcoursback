package cmr.notep.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import cmr.notep.dao.DistributeursEntity;
import cmr.notep.dao.FamillesEntity;
import cmr.notep.dao.PrecoMouvementsEntity;
import cmr.notep.dao.PrecoMouvementsQtesEntity;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.Familles;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.repository.DistributeursRepository;
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

import static cmr.notep.util.BeanCopyUtils.copyPropertiesExcludingCollections;


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
                .orElseThrow(()->new  ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Missions non enregistré")), PrecoMouvements.class);
    }

    public PrecoMouvements posterPrecomouvement(PrecoMouvements preco) throws ParcoursException {
        PrecoMouvementsRepository precoRepository = daoAccessorService.getRepository(PrecoMouvementsRepository.class);

        PrecoMouvementsEntity precoEntity = preparerPrecoMouvement(preco, precoRepository);
        PrecoMouvementsEntity savedPrecoEntity = precoRepository.save(precoEntity);

        synchroniserQuantites(preco.getPrecoMouvementsQtes(), savedPrecoEntity);

        return dozerMapperBean.map(precoRepository.save(savedPrecoEntity), PrecoMouvements.class);
    }

    private PrecoMouvementsEntity preparerPrecoMouvement(PrecoMouvements preco,
                                                          PrecoMouvementsRepository precoRepository) {
        if (preco.getId() == null) {
            PrecoMouvementsEntity precoEntity = dozerMapperBean.map(preco, PrecoMouvementsEntity.class);
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
            return precoEntity;
        }

        PrecoMouvementsEntity precoEntity = precoRepository.findById(preco.getId())
                .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "PrecoMouvement non trouvé : " + preco.getId()));
        copyPropertiesExcludingCollections(preco, precoEntity);
        initialiserCollectionQuantites(precoEntity);
        return precoEntity;
    }

    private void synchroniserQuantites(List<PrecoMouvementsQtes> qtesDemandes,
                                       PrecoMouvementsEntity precoEntity) throws ParcoursException {
        initialiserCollectionQuantites(precoEntity);
        supprimerQuantitesAbsentes(qtesDemandes, precoEntity);

        if (CollectionUtils.isEmpty(qtesDemandes)) {
            return;
        }

        for (PrecoMouvementsQtes qteDemandee : qtesDemandes) {
            PrecoMouvementsQtesEntity savedQte = enregistrerQuantite(qteDemandee, precoEntity);
            synchroniserFamilles(savedQte, qteDemandee.getFamilles());
            synchroniserDistributeurs(savedQte, qteDemandee.getDistributeurs());
            remplacerOuAjouterQuantite(precoEntity, savedQte);
        }
    }

    private void supprimerQuantitesAbsentes(List<PrecoMouvementsQtes> qtesDemandes,
                                            PrecoMouvementsEntity precoEntity) throws ParcoursException {
        PrecoMouvementsQtesRepository qtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);
        Set<String> idsDemandes = CollectionUtils.isEmpty(qtesDemandes)
                ? Collections.emptySet()
                : qtesDemandes.stream()
                .filter(q -> q.getId() != null)
                .map(PrecoMouvementsQtes::getId)
                .collect(Collectors.toSet());

        for (PrecoMouvementsQtesEntity existing : new ArrayList<>(precoEntity.getPrecoMouvementsQteEntities())) {
            if (!idsDemandes.contains(existing.getId())) {
                try {
                    detacherRelationsProprietaires(existing);
                    qtesRepo.delete(existing);
                    precoEntity.getPrecoMouvementsQteEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer la quantité " + existing.getId() + ": " + e.getMessage(), e);
                }
            }
        }
    }

    private PrecoMouvementsQtesEntity enregistrerQuantite(PrecoMouvementsQtes qteDemandee,
                                                           PrecoMouvementsEntity precoEntity) throws ParcoursException {
        PrecoMouvementsQtesRepository qtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);

        PrecoMouvementsQtesEntity qteEntity = chargerOuCreerQuantite(qteDemandee, qtesRepo);
        //appliquerChampsQuantite(qteDemandee, qteEntity);
        qteEntity.setRessourcesEntity(resoudreRessource(qteDemandee));
        qteEntity.setPrecoMouvementsEntity(precoEntity);

        try {
            return qtesRepo.save(qteEntity);
        } catch (Exception e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                    "Impossible d'enregistrer la quantité du précomouvement: " + e.getMessage(), e);
        }
    }

    private PrecoMouvementsQtesEntity chargerOuCreerQuantite(PrecoMouvementsQtes qteDemandee,
                                                              PrecoMouvementsQtesRepository qtesRepo) {
        if (qteDemandee.getId() == null) {
            return new PrecoMouvementsQtesEntity();
        }

        return qtesRepo.findById(qteDemandee.getId())
                .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "PrecoMouvementsQte non trouvée : " + qteDemandee.getId()));
    }

    private void appliquerChampsQuantite(PrecoMouvementsQtes qteDemandee, PrecoMouvementsQtesEntity qteEntity) {
        qteEntity.setQteMin(qteDemandee.getQuantiteMin());
        qteEntity.setQteMax(qteDemandee.getQuantiteMax());
        qteEntity.setMontantMin(qteDemandee.getMontantMin());
        qteEntity.setMontantMax(qteDemandee.getMontantMax());

        if (qteEntity.getId() == null) {
            qteEntity.setDateCreation(qteDemandee.getDateCreation());
        }
    }

    private RessourcesEntity resoudreRessource(PrecoMouvementsQtes qteDemandee) throws ParcoursException {
        if (qteDemandee.getRessource() == null) {
            return null;
        }

        String ressourceId = qteDemandee.getRessource().getId();
        if (ressourceId == null) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                    "Ressource invalide: id manquant sur la quantité du précomouvement");
        }

        return daoAccessorService.getRepository(RessourcesRepository.class)
                .findById(ressourceId)
                .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Ressource non trouvée : " + ressourceId));
    }

    private void synchroniserFamilles(PrecoMouvementsQtesEntity qteEntity,
                                      List<Familles> famillesDemandees) throws ParcoursException {
        FamillesRepository famillesRepository = daoAccessorService.getRepository(FamillesRepository.class);
        Set<String> idsCibles = extraireIdsCibles(
                famillesDemandees,
                Familles::getId,
                "Famille invalide: id manquant sur la quantité du précomouvement"
        );

        for (FamillesEntity familleExistante : new ArrayList<>(safeList(qteEntity.getFamillesEntities()))) {
            if (!idsCibles.contains(familleExistante.getId())) {
                retirerLienQuantite(familleExistante.getPrecoMouvementsQtesEntities(), qteEntity.getId());
                famillesRepository.save(familleExistante);
            }
        }

        List<FamillesEntity> famillesSynchronisees = new ArrayList<>();
        for (String familleId : idsCibles) {
            FamillesEntity familleEntity = famillesRepository.findById(familleId)
                    .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED, "Famille non trouvée : " + familleId));
            initialiserCollectionQuantites(familleEntity);
            ajouterLienQuantiteSiAbsent(familleEntity.getPrecoMouvementsQtesEntities(), qteEntity);
            famillesRepository.save(familleEntity);
            famillesSynchronisees.add(familleEntity);
        }

        qteEntity.setFamillesEntities(famillesSynchronisees);
    }

    private void synchroniserDistributeurs(PrecoMouvementsQtesEntity qteEntity,
                                           List<Distributeurs> distributeursDemandes) throws ParcoursException {
        DistributeursRepository distributeursRepository = daoAccessorService.getRepository(DistributeursRepository.class);
        Set<String> idsCibles = extraireIdsCibles(
                distributeursDemandes,
                Distributeurs::getId,
                "Distributeur invalide: id manquant sur la quantité du précomouvement"
        );

        for (DistributeursEntity distributeurExistant : new ArrayList<>(safeList(qteEntity.getDistributeursEntities()))) {
            if (!idsCibles.contains(distributeurExistant.getId())) {
                retirerLienQuantite(distributeurExistant.getPrecoMouvementsQtesEntities(), qteEntity.getId());
                distributeursRepository.save(distributeurExistant);
            }
        }

        List<DistributeursEntity> distributeursSynchronises = new ArrayList<>();
        for (String distributeurId : idsCibles) {
            DistributeursEntity distributeurEntity = distributeursRepository.findById(distributeurId)
                    .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED, "Distributeur non trouvé : " + distributeurId));
            initialiserCollectionQuantites(distributeurEntity);
            ajouterLienQuantiteSiAbsent(distributeurEntity.getPrecoMouvementsQtesEntities(), qteEntity);
            distributeursRepository.save(distributeurEntity);
            distributeursSynchronises.add(distributeurEntity);
        }

        qteEntity.setDistributeursEntities(distributeursSynchronises);
    }

    private void detacherRelationsProprietaires(PrecoMouvementsQtesEntity qteEntity) {
        FamillesRepository famillesRepository = daoAccessorService.getRepository(FamillesRepository.class);
        for (FamillesEntity familleEntity : new ArrayList<>(safeList(qteEntity.getFamillesEntities()))) {
            retirerLienQuantite(familleEntity.getPrecoMouvementsQtesEntities(), qteEntity.getId());
            famillesRepository.save(familleEntity);
        }
        qteEntity.setFamillesEntities(new ArrayList<>());

        DistributeursRepository distributeursRepository = daoAccessorService.getRepository(DistributeursRepository.class);
        for (DistributeursEntity distributeurEntity : new ArrayList<>(safeList(qteEntity.getDistributeursEntities()))) {
            retirerLienQuantite(distributeurEntity.getPrecoMouvementsQtesEntities(), qteEntity.getId());
            distributeursRepository.save(distributeurEntity);
        }
        qteEntity.setDistributeursEntities(new ArrayList<>());
    }

    private <T> Set<String> extraireIdsCibles(List<T> elements,
                                              Function<T, String> idExtractor,
                                              String messageIdManquant) throws ParcoursException {
        if (CollectionUtils.isEmpty(elements)) {
            return Collections.emptySet();
        }

        Set<String> ids = new LinkedHashSet<>();
        for (T element : elements) {
            String id = idExtractor.apply(element);
            if (id == null) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED, messageIdManquant);
            }
            ids.add(id);
        }
        return ids;
    }

    private void remplacerOuAjouterQuantite(PrecoMouvementsEntity precoEntity, PrecoMouvementsQtesEntity savedQte) {
        initialiserCollectionQuantites(precoEntity);
        precoEntity.getPrecoMouvementsQteEntities().removeIf(qte -> Objects.equals(qte.getId(), savedQte.getId()));
        precoEntity.getPrecoMouvementsQteEntities().add(savedQte);
    }

    private void ajouterLienQuantiteSiAbsent(List<PrecoMouvementsQtesEntity> quantites,
                                             PrecoMouvementsQtesEntity qteEntity) {
        if (!contientQuantite(quantites, qteEntity.getId())) {
            quantites.add(qteEntity);
        }
    }

    private boolean contientQuantite(List<PrecoMouvementsQtesEntity> quantites, String qteId) {
        return quantites.stream().anyMatch(qte -> Objects.equals(qte.getId(), qteId));
    }

    private void retirerLienQuantite(List<PrecoMouvementsQtesEntity> quantites, String qteId) {
        if (quantites != null) {
            quantites.removeIf(qte -> Objects.equals(qte.getId(), qteId));
        }
    }

    private <T> List<T> safeList(List<T> quantites) {
        return quantites == null ? new ArrayList<>() : quantites;
    }

    private void initialiserCollectionQuantites(PrecoMouvementsEntity precoEntity) {
        if (precoEntity.getPrecoMouvementsQteEntities() == null) {
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
        }
    }

    private void initialiserCollectionQuantites(FamillesEntity familleEntity) {
        if (familleEntity.getPrecoMouvementsQtesEntities() == null) {
            familleEntity.setPrecoMouvementsQtesEntities(new ArrayList<>());
        }
    }

    private void initialiserCollectionQuantites(DistributeursEntity distributeurEntity) {
        if (distributeurEntity.getPrecoMouvementsQtesEntities() == null) {
            distributeurEntity.setPrecoMouvementsQtesEntities(new ArrayList<>());
        }
    }

    public void supprimerPrecomouvement(PrecoMouvements precomouvement) {
        daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .deleteById(precomouvement.getId().toString());
    }

}
