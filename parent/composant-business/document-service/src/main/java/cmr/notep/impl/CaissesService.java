package cmr.notep.impl;

import cmr.notep.api.ICaisses;
import cmr.notep.business.CaissesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Caisses;

import java.util.List;

public class CaissesService implements ICaisses {

    private final CaissesBusiness caissesBusiness ;

    public CaissesService(CaissesBusiness caissesBusiness) {
        this.caissesBusiness = caissesBusiness;
    }

    @Override
    public Caisses avoircaisse(String idcaisse) throws ParcoursException {
        return null;
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
        return null;
    }
}
