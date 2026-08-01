package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.DistributeursEntity;
import cmr.notep.dao.FamillesEntity;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.dao.PromotionsEntity;
import cmr.notep.modele.Promotions;
import cmr.notep.modele.Ressources;
import cmr.notep.repository.DistributeursRepository;
import cmr.notep.repository.FamillesRepository;
import cmr.notep.repository.PromotionsRepository;
import cmr.notep.repository.RessourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class PromotionsBusiness {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public PromotionsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Promotions avoirPromotion(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PromotionsRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("promotion inexistante")), Promotions.class);
    }

    public List<Promotions> avoirToutPromotions() {
        return daoAccessorService.getRepository(PromotionsRepository.class).findAll()
                .stream().map(promotion ->dozerMapperBean.map(promotion, Promotions.class))
                .collect(Collectors.toList());
    }

    public void supprimerPromotion(Promotions Promotions)
    {
        daoAccessorService.getRepository(PromotionsRepository.class)
                .deleteById(Promotions.getId());
    }

    public Promotions posterPromotion(Promotions promotion) {
        // Mémoriser les ressources actuellement liées avant modification (cas update)
        Set<String> anciensRessourceIds = chargerAnciensRessourceIds(promotion.getId());

        PromotionsEntity promotionEntity = dozerMapperBean.map(promotion, PromotionsEntity.class);
        lierDistributeur(promotion, promotionEntity);
        lierFamilles(promotion, promotionEntity);

        PromotionsEntity promotionSauvegardee = daoAccessorService.getRepository(PromotionsRepository.class)
                .save(promotionEntity);

        synchroniserRessources(promotion, promotionSauvegardee, anciensRessourceIds);

        return dozerMapperBean.map(promotionSauvegardee, Promotions.class);
    }

    /**
     * Charge les IDs des ressources actuellement liées à une promotion existante.
     * Nécessaire pour identifier les ressources à dissocier lors d'une mise à jour,
     * car {@code ressourcespromotions} est géré côté propriétaire ({@link RessourcesEntity}).
     */
    private Set<String> chargerAnciensRessourceIds(String promotionId) {
        if (promotionId == null) {
            return Collections.emptySet();
        }
        return daoAccessorService.getRepository(PromotionsRepository.class)
                .findById(promotionId)
                .map(p -> p.getRessourcesEntities() == null
                        ? Collections.<String>emptySet()
                        : p.getRessourcesEntities().stream()
                                .map(RessourcesEntity::getId)
                                .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private void lierDistributeur(Promotions promotion, PromotionsEntity promotionEntity) {
        if (promotion.getDistributeur() == null || promotion.getDistributeur().getId() == null) {
            return;
        }
        DistributeursEntity distributeur = daoAccessorService.getRepository(DistributeursRepository.class)
                .findById(promotion.getDistributeur().getId())
                .orElseThrow(() -> new RuntimeException("Distributeur inexistant"));
        promotionEntity.setDistributeursEntity(distributeur);
    }

    private void lierFamilles(Promotions promotion, PromotionsEntity promotionEntity) {
        if (promotion.getFamilles() == null) {
            return;
        }
        List<FamillesEntity> famillesEntities = promotion.getFamilles().stream()
                .filter(Objects::nonNull)
                .filter(famille -> famille.getId() != null)
                .map(famille -> daoAccessorService.getRepository(FamillesRepository.class)
                        .findById(famille.getId())
                        .orElseThrow(() -> new RuntimeException("Famille inexistante: " + famille.getId())))
                .collect(Collectors.toList());
        promotionEntity.setFamillesEntities(famillesEntities);
    }

    /**
     * Synchronise la table de jointure {@code ressourcespromotions} dont le côté propriétaire
     * est {@link RessourcesEntity} : dissocie les ressources retirées de la liste,
     * puis associe les nouvelles.
     */
    private void synchroniserRessources(Promotions promotion, PromotionsEntity promotionSauvegardee,
                                        Set<String> anciensRessourceIds) {
        Set<String> nouvellesRessourceIds = promotion.getRessources() == null
                ? Collections.emptySet()
                : promotion.getRessources().stream()
                        .filter(r -> r != null && r.getId() != null)
                        .map(Ressources::getId)
                        .collect(Collectors.toSet());

        // Dissocier les ressources qui ne font plus partie de la promotion
        anciensRessourceIds.stream()
                .filter(id -> !nouvellesRessourceIds.contains(id))
                .forEach(id -> dissocierRessource(id, promotionSauvegardee));

        // Associer les nouvelles ressources (le lien est créé côté propriétaire RessourcesEntity)
        if (promotion.getRessources() == null) {
            return;
        }
        List<RessourcesEntity> ressourcesEntities = promotion.getRessources().stream()
                .filter(r -> r != null && r.getId() != null)
                .map(r -> lierRessourceAPromotion(r, promotionSauvegardee))
                .collect(Collectors.toList());
        promotionSauvegardee.setRessourcesEntities(ressourcesEntities);
    }

    private void dissocierRessource(String ressourceId, PromotionsEntity promotion) {
        daoAccessorService.getRepository(RessourcesRepository.class)
                .findById(ressourceId)
                .ifPresent(ressourceEntity -> {
                    if (ressourceEntity.getPromotionsEntities() != null) {
                        ressourceEntity.getPromotionsEntities()
                                .removeIf(p -> promotion.getId().equals(p.getId()));
                        daoAccessorService.getRepository(RessourcesRepository.class).save(ressourceEntity);
                    }
                });
    }

    private RessourcesEntity lierRessourceAPromotion(Ressources ressource, PromotionsEntity promotionSauvegardee) {
        RessourcesEntity ressourceEntity = daoAccessorService.getRepository(RessourcesRepository.class)
                .findById(ressource.getId())
                .orElseThrow(() -> new RuntimeException("Ressource inexistante: " + ressource.getId()));

        if (ressourceEntity.getPromotionsEntities() == null) {
            ressourceEntity.setPromotionsEntities(new ArrayList<>());
        }

        boolean dejaLiee = ressourceEntity.getPromotionsEntities().stream()
                .anyMatch(p -> promotionSauvegardee.getId() != null && promotionSauvegardee.getId().equals(p.getId()));
        if (!dejaLiee) {
            ressourceEntity.getPromotionsEntities().add(promotionSauvegardee);
        }

        return daoAccessorService.getRepository(RessourcesRepository.class).save(ressourceEntity);
    }

}
