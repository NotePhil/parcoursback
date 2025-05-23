package cmr.notep.impl;

import cmr.notep.api.IEtatsApi;
import cmr.notep.business.EtatsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Etats;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class EtatsService implements IEtatsApi {
    private final EtatsBusiness etatsBusiness;

    public EtatsService(EtatsBusiness etatsBusiness) {
        this.etatsBusiness = etatsBusiness;
    }

    @Override
    public Etats posterEtat(Etats Etat) {
        return null;
    }

    @Override
    public Etats avoirEtat(String idEtat) throws ParcoursException {
        return etatsBusiness.avoirEtat(idEtat);
    }

    @Override
    public List<Etats> avoirTousEtats() {
        return null;
    }

    @Override
    public void SupprimerEtat(Etats Etat) {

    }
}
