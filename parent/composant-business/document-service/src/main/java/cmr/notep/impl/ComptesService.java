package cmr.notep.impl;


import cmr.notep.api.IComptesApi;
import cmr.notep.business.ComptesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Comptes;
import jakarta.transaction.Transactional;
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
    public Comptes avoirCompte(String idCompte) throws ParcoursException {
        return this.comptesBusiness.avoirComptes(idCompte);
    }

    public List<Comptes> avoirToutComptes() {
        return this.comptesBusiness.avoirToutComptes();
    }

    @Override
    public Comptes posterCompte(Comptes comptes) {
        return this.comptesBusiness.posterCcompte(comptes);
    }


    public void supprimerComptes(Comptes comptes) {

    }


    public Comptes posterComptes(Comptes comptes) {
        return null;
    }
}
