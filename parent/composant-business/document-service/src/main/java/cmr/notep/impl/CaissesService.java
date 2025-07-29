package cmr.notep.impl;

import cmr.notep.api.ICaisses;
import cmr.notep.business.CaissesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Caisses;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class CaissesService implements ICaisses {

    private final CaissesBusiness caissesBusiness ;

    public CaissesService(CaissesBusiness caissesBusiness) {
        this.caissesBusiness = caissesBusiness;
    }

    @Override
    public Caisses avoircaisse(String idcaisse) throws ParcoursException {
        return caissesBusiness.avoirCaisse(idcaisse);
    }

    @Override
    public List<Caisses> avoirToutescaisses() {
        return caissesBusiness.avoirToutesCaisses();
    }

    @Override
    public void supprimercaisse(Caisses caisse) {

    }

    @Override
    public Caisses postercaisse(Caisses caisse) {
        return caissesBusiness.posterCaisse(caisse);
    }
}
