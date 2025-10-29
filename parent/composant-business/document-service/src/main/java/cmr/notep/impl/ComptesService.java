package cmr.notep.impl;

import cmr.notep.api.IComptesApi;
import cmr.notep.business.ComptesBusiness;
import cmr.notep.modele.Comptes;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class ComptesService implements IComptesApi {

    private final ComptesBusiness comptesBusiness ;

    public ComptesService(ComptesBusiness comptesBusiness) {
        this.comptesBusiness = comptesBusiness;
    }

    @Override
    public Comptes avoirComptes(@NonNull String idComptes) { return this.comptesBusiness.avoirComptes(idComptes);}

    @Override
    public List<Comptes> avoirToutComptes(){return this.comptesBusiness.avoirToutComptes();}

    @Override
    public void supprimerComptes(@NonNull Comptes comptes){
        this.comptesBusiness.supprimerCompte(comptes);
    }

    @Override
    public Comptes posterComptes(@NonNull Comptes comptes) {
        return this.comptesBusiness.posterComptes(comptes);
    }

}
