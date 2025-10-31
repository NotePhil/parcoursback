package cmr.notep.impl;

import cmr.notep.api.IComptesApi;
import cmr.notep.business.ComptesBusiness;
import cmr.notep.modele.Comptes;
import lombok.NonNull;

import java.util.List;

public class ComptesService implements IComptesApi {

    private final ComptesBusiness compteBusiness ;

    public ComptesService(ComptesBusiness compteBusiness) {
        this.compteBusiness = compteBusiness ;
    }

    @Override
    public Comptes avoirComptes(@NonNull String idCompte) { return compteBusiness.avoirCompte(idCompte);}

    @Override
    public List<Comptes> avoirToutCompte(){return compteBusiness.avoirToutComptes();}

    @Override
    public void supprimerCompte(@NonNull Comptes compte){
        compteBusiness.supprimerCompte(compte);
    }

    @Override
    public Comptes posterCompte(@NonNull Comptes comptes) {
        return compteBusiness.posterCompte(comptes);
    }
}
