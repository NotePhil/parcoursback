package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Actions;
import cmr.notep.modele.Utilisateurs;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("utilisateurs")
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
    Actions posterUser(@NonNull @RequestBody Utilisateurs user);

}
