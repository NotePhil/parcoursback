package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.PromotionsEntity;
import cmr.notep.modele.Promotions;
import cmr.notep.repository.PromotionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .deleteById(Promotions.getId().toString());
    }

    public Promotions posterPromotion(Promotions Promotions) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(PromotionsRepository.class)
                .save(dozerMapperBean.map(Promotions, PromotionsEntity.class)), Promotions.class);
    }
    
}
