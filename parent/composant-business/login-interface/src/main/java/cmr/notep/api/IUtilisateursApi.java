package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.LoginForm;
import cmr.notep.modele.Utilisateurs;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("users")
public interface IUtilisateursApi {

    @GetMapping(
            path = "/{iduser}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Utilisateurs avoirUser (@NonNull @PathVariable(name = "iduser") String iduser) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Utilisateurs> avoirToutUser();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerUser(@NonNull @RequestBody Utilisateurs user);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Utilisateurs posterUser(@NonNull @RequestBody Utilisateurs user);

    @PostMapping(
            path = "/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    String authenticateAndGetToken(@RequestBody LoginForm loginForm);

}
