package cmr.notep.impl;

import cmr.notep.api.IPromotionsApi;
import cmr.notep.business.PromotionsBusiness;
import cmr.notep.modele.Promotions;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class PromotionsService implements IPromotionsApi {

    private final PromotionsBusiness promotionsBusiness;

    public PromotionsService(PromotionsBusiness promotionsBusiness) {
        this.promotionsBusiness = promotionsBusiness;
    }

    @Override
    public Promotions avoirPromotion(String idPromotions) {
        return promotionsBusiness.avoirPromotion(idPromotions);
    }

    @Override
    public List<Promotions> avoirToutPromotions() {
        System.out.print("tester promotions");
        return promotionsBusiness.avoirToutPromotions();
    }

    @Override
    public void supprimerPromotion(Promotions Promotions) {
        promotionsBusiness.supprimerPromotion(Promotions);
    }

    @Override
    public Promotions posterPromotion(Promotions Promotions) {
        return promotionsBusiness.posterPromotion(Promotions);
    }
}
