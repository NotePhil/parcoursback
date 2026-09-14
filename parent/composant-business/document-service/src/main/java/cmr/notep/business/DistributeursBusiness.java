package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Distributeurs;
import cmr.notep.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
                .deleteById(Distributeurs.getId());
    }

    public Distributeurs posterDistributeur(Distributeurs distributeur) throws ParcoursException {
        DistributeursRepository distributeursRepo = daoAccessorService.getRepository(DistributeursRepository.class);
        DistributeursEntity distributeursEntity;

        if (distributeur.getId() != null) {
            distributeursEntity = distributeursRepo.findById(distributeur.getId())
                    .orElseThrow(() -> new RuntimeException("Distributeur non trouvé : " + distributeur.getId()));
            List<PrecoMouvementsQtesEntity> precoMouvementsQtesEntities = distributeursEntity.getPrecoMouvementsQtesEntities() == null
                    ? null
                    : new ArrayList<>(distributeursEntity.getPrecoMouvementsQtesEntities());
            List<PromotionsEntity> promotionsEntities = distributeursEntity.getPromotionsEntities() == null
                    ? null
                    : new ArrayList<>(distributeursEntity.getPromotionsEntities());
            dozerMapperBean.map(distributeur, distributeursEntity);
            distributeursEntity.setPrecoMouvementsQtesEntities(precoMouvementsQtesEntities);
            distributeursEntity.setPromotionsEntities(promotionsEntities);
        } else {
            distributeursEntity = dozerMapperBean.map(distributeur, DistributeursEntity.class);
        }

        distributeursEntity = distributeursRepo.save(distributeursEntity);
        return dozerMapperBean.map(distributeursEntity, Distributeurs.class);
    }
}
