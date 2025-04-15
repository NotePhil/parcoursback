package cmr.notep.impl;

import cmr.notep.api.IUtilisateursApi;
import cmr.notep.business.UtilisateursBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Actions;
import cmr.notep.modele.Utilisateurs;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class UtilisateursService implements IUtilisateursApi {

    private final UtilisateursBusiness utilisateursBusiness;

    public UtilisateursService(UtilisateursBusiness utilisateursBusiness) {
        this.utilisateursBusiness = utilisateursBusiness;
    }

    @Override
    public Utilisateurs avoirUser(String iduser) throws ParcoursException {
        return null;
    }

    @Override
    public List<Utilisateurs> avoirToutUser() {
        return utilisateursBusiness.avoirToutUser();
    }

    @Override
    public void supprimerUser(Utilisateurs user) {

    }

    @Override
    public Actions posterUser(Utilisateurs user) {
        return null;
    }
}
