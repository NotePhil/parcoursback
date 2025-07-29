package cmr.notep.impl;

import cmr.notep.api.IMouvementCaisses;
import cmr.notep.business.MouvementCaissesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.MouvementCaisses;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class MouvementCaissesService implements IMouvementCaisses {

    private final MouvementCaissesBusiness mouvcaisse ;

    public MouvementCaissesService(MouvementCaissesBusiness mouvcaisse) {
        this.mouvcaisse = mouvcaisse;
    }

    @Override
    public MouvementCaisses avoirmouvementcaisse(String idmouvementcaisse) throws ParcoursException {
        return null;
    }

    @Override
    public List<MouvementCaisses> avoirToutmouvementcaisse() {
        return mouvcaisse.avoirToutMouvementCaisses();
    }

    @Override
    public void supprimermouvementcaisse(MouvementCaisses mouvementcaisse) {

    }

    @Override
    public MouvementCaisses postermouvementcaisse(MouvementCaisses mouvementcaisse) {
        return null;
    }
}
