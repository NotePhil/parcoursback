package cmr.notep.impl;


import cmr.notep.business.ComptesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Comptes;

import java.util.List;

public class ComptesService  {

    private final ComptesBusiness comptesBusiness ;

    public ComptesService(ComptesBusiness comptesBusiness) {
        this.comptesBusiness = comptesBusiness;
    }


    public Comptes avoircompte(String idcompte) throws ParcoursException {
        return null;
    }


    public List<Comptes> avoirToutComptes() {
        return comptesBusiness.avoirToutComptes();
    }


    public void supprimerComptes(Comptes comptes) {

    }


    public Comptes posterComptes(Comptes comptes) {
        return null;
    }
}
