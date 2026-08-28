package cmr.notep.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import cmr.notep.config.mapper.MapperBean;
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
        if(CollectionUtils.isEmpty(preco.getPrecoMouvementsQtes())) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED, "Le précomouvement doit contenir au moins une quantité");
        }
        PrecoMouvementsRepository precoRepository = daoAccessorService.getRepository(PrecoMouvementsRepository.class);

        PrecoMouvementsEntity precoEntity = preparerPrecoMouvement(preco, precoRepository);

        synchroniserQuantites(preco.getPrecoMouvementsQtes(), precoEntity);

        return dozerMapperBean.map(precoRepository.save(precoEntity), PrecoMouvements.class);
    }

    private PrecoMouvementsEntity preparerPrecoMouvement(PrecoMouvements preco,
                                                          PrecoMouvementsRepository precoRepository) {
        PrecoMouvementsEntity precoEntity;
        if (preco.getId() == null) {
            precoEntity = dozerMapperBean.map(preco, PrecoMouvementsEntity.class);
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
        }
        else {
            precoEntity = precoRepository.findById(preco.getId())
                    .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "PrecoMouvement non trouvé : " + preco.getId()));
            MapperBean.getDozerMapperWithoutList().map(preco, precoEntity);
        }
        precoEntity = precoRepository.save(precoEntity);
        return precoEntity;
    }

    private void synchroniserQuantites(List<PrecoMouvementsQtes> qtesDemandes,
                                       PrecoMouvementsEntity precoEntity) throws ParcoursException {
        supprimerQuantitesAbsentes(qtesDemandes, precoEntity);
        List<PrecoMouvementsQtesEntity> savedQtes = new ArrayList<>();
        for (PrecoMouvementsQtes qteDemandee : qtesDemandes) {
            PrecoMouvementsQtesEntity savedQte =
                    enregistrerQuantite(qteDemandee, precoEntity);
            savedQtes.add(savedQte);
        }
        if(precoEntity.getPrecoMouvementsQteEntities() != null)
            precoEntity.getPrecoMouvementsQteEntities().clear();
        precoEntity.setPrecoMouvementsQteEntities(savedQtes);
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

        PrecoMouvementsQtesEntity qteEntity = chargerOuCreerQuantite(qteDemandee,precoEntity, qtesRepo);

        try {
            return qtesRepo.save(qteEntity);
        } catch (Exception e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                    "Impossible d'enregistrer la quantité du précomouvement: " + e.getMessage(), e);
        }
    }

    private PrecoMouvementsQtesEntity chargerOuCreerQuantite(PrecoMouvementsQtes qteDemandee,
                                                              PrecoMouvementsEntity precoEntity,
                                                              PrecoMouvementsQtesRepository qtesRepo) {
        if (qteDemandee.getId() == null) {
            PrecoMouvementsQtesEntity qteEntity = dozerMapperBean.map(qteDemandee, PrecoMouvementsQtesEntity.class);
            qteEntity.setPrecoMouvementsEntity(precoEntity);
            return qteEntity;
        }
        else {
            PrecoMouvementsQtesEntity qteEntity = qtesRepo.findById(qteDemandee.getId())
                .orElseThrow(() -> new  ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "PrecoMouvementsQte non trouvée : " + qteDemandee.getId()));
            if(qteEntity.getDistributeursEntities() != null )
                qteEntity.getDistributeursEntities().clear();
            if(qteEntity.getFamillesEntities() != null )
                qteEntity.getFamillesEntities().clear();
            dozerMapperBean.map(qteDemandee, qteEntity);
            qteEntity.setPrecoMouvementsEntity(precoEntity);
            return qteEntity;
        }
    }


    private void remplacerOuAjouterQuantite(PrecoMouvementsEntity precoEntity, PrecoMouvementsQtesEntity savedQte) {
        initialiserCollectionQuantites(precoEntity);
        precoEntity.getPrecoMouvementsQteEntities().removeIf(qte -> Objects.equals(qte.getId(), savedQte.getId()));
        precoEntity.getPrecoMouvementsQteEntities().add(savedQte);
    }

    private void initialiserCollectionQuantites(PrecoMouvementsEntity precoEntity) {
        if (precoEntity.getPrecoMouvementsQteEntities() == null) {
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
        }
    }


    public void supprimerPrecomouvement(PrecoMouvements precomouvement) {
        daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .deleteById(precomouvement.getId().toString());
    }

}
