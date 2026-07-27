package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Groupe;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/groupes")
public interface IGroupeApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Groupe posterGroupe(@NonNull @RequestBody Groupe groupe) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Groupe avoirGroupe(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Groupe> avoirTousGroupes() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Groupe modifierGroupe(@NonNull @PathVariable String id, @NonNull @RequestBody Groupe groupe) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerGroupe(@NonNull @PathVariable String id) throws ParcoursException;
}
