package cmr.notep.impl;

import cmr.notep.api.IComptes;
import cmr.notep.business.ComptesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Comptes;

import java.util.List;

public class ComptesService implements IComptes {

    private final ComptesBusiness comptesBusiness ;

    public ComptesService(ComptesBusiness comptesBusiness) {
        this.comptesBusiness = comptesBusiness;
    }

    @Override
    public Comptes avoircompte(String idcompte) throws ParcoursException {
        return null;
    }

    @Override
    public List<Comptes> avoirToutComptes() {
        return comptesBusiness.avoirToutComptes();
    }

    @Override
    public void supprimerComptes(Comptes comptes) {

    }

    @Override
    public Comptes posterComptes(Comptes comptes) {
        return null;
    }
}
