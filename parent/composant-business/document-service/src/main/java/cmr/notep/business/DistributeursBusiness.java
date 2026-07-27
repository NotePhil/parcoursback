package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.Promotions;
import cmr.notep.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class DistributeursBusiness {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;
    
    public DistributeursBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Distributeurs avoirDistributeur(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DistributeursRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("Categorie inexistante")), Distributeurs.class);
    }

    public List<Distributeurs> avoirToutDistributeurs() {
        return daoAccessorService.getRepository(DistributeursRepository.class).findAll()
                .stream().map(cat ->dozerMapperBean.map(cat, Distributeurs.class))
                .collect(Collectors.toList());
    }

    public void supprimerDistributeur(Distributeurs Distributeurs)
    {
        daoAccessorService.getRepository(DistributeursRepository.class)
                .deleteById(Distributeurs.getId().toString());
    }

    public Distributeurs posterDistributeur(Distributeurs distributeur) throws ParcoursException {
        DistributeursRepository distributeursRepo = daoAccessorService.getRepository(DistributeursRepository.class);
        DistributeursEntity distributeursEntity;

        if (distributeur.getId() != null) {
            distributeursEntity = distributeursRepo.findById(distributeur.getId())
                    .orElseThrow(() -> new RuntimeException("Distributeur non trouvé : " + distributeur.getId()));
            dozerMapperBean.map(distributeur, distributeursEntity);
        } else {
            distributeursEntity = dozerMapperBean.map(distributeur, DistributeursEntity.class);
        }

        gererPrecoMouvementsQtesDistributeur(distributeur, distributeursEntity);
        gererPromotionsDistributeur(distributeur, distributeursEntity);

        distributeursEntity = distributeursRepo.save(distributeursEntity);
        return dozerMapperBean.map(distributeursEntity, Distributeurs.class);
    }

    private void gererPrecoMouvementsQtesDistributeur(Distributeurs distributeur, DistributeursEntity distributeursEntity) throws ParcoursException {
        if (CollectionUtils.isEmpty(distributeur.getPrecoMouvementsQtes())) {
            try {
                if (distributeursEntity.getPrecoMouvementsQtesEntities() != null) {
                    for (PrecoMouvementsQtesEntity existing : new ArrayList<>(distributeursEntity.getPrecoMouvementsQtesEntities())) {
                        distributeursEntity.getPrecoMouvementsQtesEntities().remove(existing);
                    }
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les precoMouvementsQtes du distributeur: " + e.getMessage(), e);
            }
            return;
        }

        PrecoMouvementsQtesRepository precoMvtQtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);
        List<PrecoMouvementsQtesEntity> existingQtes = distributeursEntity.getPrecoMouvementsQtesEntities() != null
                ? new ArrayList<>(distributeursEntity.getPrecoMouvementsQtesEntities())
                : new ArrayList<>();

        List<String> newIds = distributeur.getPrecoMouvementsQtes().stream()
                .filter(q -> q.getId() != null)
                .map(cmr.notep.modele.PrecoMouvementsQtes::getId)
                .toList();

        for (PrecoMouvementsQtesEntity existing : existingQtes) {
            if (!newIds.contains(existing.getId())) {
                try {
                    distributeursEntity.getPrecoMouvementsQtesEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer la precoMouvementsQte: " + e.getMessage(), e);
                }
            }
        }

        if (distributeursEntity.getPrecoMouvementsQtesEntities() == null) {
            distributeursEntity.setPrecoMouvementsQtesEntities(new ArrayList<>());
        }
    }

    private void gererPromotionsDistributeur(Distributeurs distributeur, DistributeursEntity distributeursEntity) throws ParcoursException {
        if (CollectionUtils.isEmpty(distributeur.getPromotions())) {
            try {
                if (distributeursEntity.getPromotionsEntities() != null) {
                    PromotionsRepository promotionsRepo = daoAccessorService.getRepository(PromotionsRepository.class);
                    for (PromotionsEntity existing : new ArrayList<>(distributeursEntity.getPromotionsEntities())) {
                        promotionsRepo.delete(existing);
                    }
                    distributeursEntity.getPromotionsEntities().clear();
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les promotions du distributeur: " + e.getMessage(), e);
            }
            return;
        }

        PromotionsRepository promotionsRepo = daoAccessorService.getRepository(PromotionsRepository.class);
        List<PromotionsEntity> existingPromotions = distributeursEntity.getPromotionsEntities() != null
                ? new ArrayList<>(distributeursEntity.getPromotionsEntities())
                : new ArrayList<>();

        List<String> newIds = distributeur.getPromotions().stream()
                .filter(p -> p.getId() != null)
                .map(Promotions::getId)
                .toList();

        for (PromotionsEntity existing : existingPromotions) {
            if (!newIds.contains(existing.getId())) {
                try {
                    promotionsRepo.delete(existing);
                    distributeursEntity.getPromotionsEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer la promotion: " + e.getMessage(), e);
                }
            }
        }

        if (distributeursEntity.getPromotionsEntities() == null) {
            distributeursEntity.setPromotionsEntities(new ArrayList<>());
        }

        for (Promotions promotion : distributeur.getPromotions()) {
            PromotionsEntity promEntity;
            if (promotion.getId() != null) {
                promEntity = promotionsRepo.findById(promotion.getId())
                        .orElseThrow(() -> new RuntimeException("Promotion non trouvée : " + promotion.getId()));
                dozerMapperBean.map(promotion, promEntity);
            } else {
                promEntity = dozerMapperBean.map(promotion, PromotionsEntity.class);
                promEntity.setId(null);
            }
            promEntity.setDistributeursEntity(distributeursEntity);
            try {
                PromotionsEntity savedProm = promotionsRepo.save(promEntity);
                if (!distributeursEntity.getPromotionsEntities().contains(savedProm)) {
                    distributeursEntity.getPromotionsEntities().add(savedProm);
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'enregistrer la promotion du distributeur: " + e.getMessage(), e);
            }
        }
    }
}
