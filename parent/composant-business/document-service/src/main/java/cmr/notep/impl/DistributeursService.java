package cmr.notep.impl;

import cmr.notep.api.IDistributeursApi;
import cmr.notep.business.DistributeursBusiness;
import cmr.notep.modele.Distributeurs;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class DistributeursService implements IDistributeursApi {

    private final DistributeursBusiness distributeursBusiness ;

    public DistributeursService(DistributeursBusiness distributeursBusiness) {
        this.distributeursBusiness = distributeursBusiness;
    }

    @Override
    public Distributeurs avoirDistributeur(@NonNull String idDistributeurs) {
        return distributeursBusiness.avoirDistributeur(idDistributeurs);
    }

    @Override
    public List<Distributeurs> avoirToutDistributeurs() {
        System.out.println("calling avoir tout distributeur");
        return distributeursBusiness.avoirToutDistributeurs();
    }

    @Override
    public void supprimerDistributeur(@NonNull Distributeurs Distributeurs) {
            distributeursBusiness.supprimerDistributeur(Distributeurs);
    }

    @Override
    public Distributeurs posterDistributeur(@NonNull Distributeurs Distributeurs) {
        return distributeursBusiness.posterDistributeur(Distributeurs);
    }
}
