package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Utilisateur;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/utilisateurs")
public interface IUtilisateurApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Utilisateur posterUtilisateur(@NonNull @RequestBody Utilisateur utilisateur) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Utilisateur avoirUtilisateur(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Utilisateur> avoirTousUtilisateurs() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Utilisateur modifierUtilisateur(@NonNull @PathVariable String id, @NonNull @RequestBody Utilisateur utilisateur) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerUtilisateur(@NonNull @PathVariable String id) throws ParcoursException;
}
