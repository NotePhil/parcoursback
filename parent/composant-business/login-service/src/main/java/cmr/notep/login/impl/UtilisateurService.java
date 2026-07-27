package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IUtilisateurApi;
import cmr.notep.login.business.UtilisateurBusiness;
import cmr.notep.login.modele.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class UtilisateurService implements IUtilisateurApi {

    private final UtilisateurBusiness utilisateurBusiness;

    public UtilisateurService(UtilisateurBusiness utilisateurBusiness) {
        this.utilisateurBusiness = utilisateurBusiness;
    }

    @Override public Utilisateur posterUtilisateur(Utilisateur utilisateur) throws ParcoursException { return utilisateurBusiness.posterUtilisateur(utilisateur); }
    @Override public Utilisateur avoirUtilisateur(String id) throws ParcoursException { return utilisateurBusiness.avoirUtilisateur(id); }
    @Override public List<Utilisateur> avoirTousUtilisateurs() throws ParcoursException { return utilisateurBusiness.avoirTousUtilisateurs(); }
    @Override public Utilisateur modifierUtilisateur(String id, Utilisateur utilisateur) throws ParcoursException { return utilisateurBusiness.modifierUtilisateur(id, utilisateur); }
    @Override public void supprimerUtilisateur(String id) throws ParcoursException { utilisateurBusiness.supprimerUtilisateur(id); }
}
