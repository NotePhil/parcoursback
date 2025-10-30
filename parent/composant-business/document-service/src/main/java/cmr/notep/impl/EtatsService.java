package cmr.notep.impl;

import cmr.notep.api.IEtatsApi;
import cmr.notep.business.EtatsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Etats;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
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
    public Etats posterEtat(Etats etat) {
        return this.etatsBusiness.posterEtat(etat);
    }

    @Override
    public Etats avoirEtat(String idEtat) throws ParcoursException {
        return etatsBusiness.avoirEtat(idEtat);
    }

    @Override
    public List<Etats> avoirTousEtats() {
        return this.etatsBusiness.avoirToutEtats();
    }

    @Override
    public void SupprimerEtat(Etats etat) {
        this.etatsBusiness.supprimerEtat(etat);
    }
}
