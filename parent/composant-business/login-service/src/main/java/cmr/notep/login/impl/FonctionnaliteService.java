package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IFonctionnaliteApi;
import cmr.notep.login.business.FonctionnaliteBusiness;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.modele.ReorderRequest;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class FonctionnaliteService implements IFonctionnaliteApi {

    private final FonctionnaliteBusiness fonctionnaliteBusiness;

    public FonctionnaliteService(FonctionnaliteBusiness fonctionnaliteBusiness) {
        this.fonctionnaliteBusiness = fonctionnaliteBusiness;
    }

    @Override public Fonctionnalite posterFonctionnalite(Fonctionnalite fonctionnalite) throws ParcoursException { return fonctionnaliteBusiness.posterFonctionnalite(fonctionnalite); }
    @Override public Fonctionnalite avoirFonctionnalite(String id) throws ParcoursException { return fonctionnaliteBusiness.avoirFonctionnalite(id); }
    @Override public List<Fonctionnalite> avoirToutesFonctionnalites() throws ParcoursException { return fonctionnaliteBusiness.avoirToutesFonctionnalites(); }
    @Override public Fonctionnalite modifierFonctionnalite(String id, Fonctionnalite fonctionnalite) throws ParcoursException { return fonctionnaliteBusiness.modifierFonctionnalite(id, fonctionnalite); }
    @Override public void supprimerFonctionnalite(String id) throws ParcoursException { fonctionnaliteBusiness.supprimerFonctionnalite(id); }
    @Override public List<Fonctionnalite> reordonnerFonctionnalites(ReorderRequest request) throws ParcoursException { return fonctionnaliteBusiness.reordonner(request); }
}
