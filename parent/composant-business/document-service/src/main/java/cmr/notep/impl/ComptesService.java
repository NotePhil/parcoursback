package cmr.notep.impl;

import cmr.notep.api.IComptesApi;
import cmr.notep.business.ComptesBusiness;
import cmr.notep.modele.Comptes;
import lombok.NonNull;

import java.util.List;

public class ComptesService implements IComptesApi {

    private final ComptesBusiness comptesBusiness;

    public ComptesService(ComptesBusiness comptesBusiness) {
        this.comptesBusiness = comptesBusiness ;
    }

    @Override
    public Comptes avoirCompte(@NonNull String idCompte) { return comptesBusiness.avoirCompte(idCompte);}

    @Override
    public List<Comptes> avoirTousComptes(){return comptesBusiness.avoirTousComptes();}

    @Override
    public void supprimerCompte(@NonNull Comptes compte){
        comptesBusiness.supprimerCompte(compte);
    }

    @Override
    public Comptes posterCompte(@NonNull Comptes comptes) {
        return comptesBusiness.posterCompte(comptes);
    }
}
