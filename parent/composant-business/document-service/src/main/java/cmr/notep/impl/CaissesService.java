package cmr.notep.impl;

import cmr.notep.api.ICaissesApi;
import cmr.notep.business.CaissesBusiness;
import cmr.notep.modele.Caisses;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class CaissesService implements ICaissesApi {

    private final CaissesBusiness caissesBusiness;

    public CaissesService(CaissesBusiness caissesBusiness) {
        this.caissesBusiness = caissesBusiness;
    }

    @Override
    public Caisses avoirCaisse(@NonNull String idCaisse) {
        return  caissesBusiness.avoirCaisse(idCaisse);
    }

    @Override
    public List<Caisses> avoirToutCaisses() {
        return caissesBusiness.avoirToutCaisses();
    }

    @Override
    public void supprimerCaisse(@NonNull Caisses caisse) {
        caissesBusiness.supprimerCaisse(caisse);
    }

    @Override
    public Caisses posterCaisse(@NonNull Caisses caisse) {
        return caissesBusiness.posterCaisse(caisse);
    }
}
