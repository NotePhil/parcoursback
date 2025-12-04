package cmr.notep.api;

import cmr.notep.modele.Promotions;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("promotions")
public interface IPromotionsApi {

    @GetMapping(
            path = "/{idPromotions}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Promotions avoirPromotion (@NonNull @PathVariable(name="idPromotions") String idPromotions);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Promotions> avoirToutPromotions();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerPromotion(@NonNull @RequestBody Promotions Promotions);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Promotions posterPromotion(@NonNull @RequestBody Promotions Promotions) ;
    
}
