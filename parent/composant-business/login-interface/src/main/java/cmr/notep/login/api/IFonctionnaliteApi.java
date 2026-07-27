package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.modele.ReorderRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/fonctionnalites")
public interface IFonctionnaliteApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Fonctionnalite posterFonctionnalite(@NonNull @RequestBody Fonctionnalite fonctionnalite) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Fonctionnalite avoirFonctionnalite(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Fonctionnalite> avoirToutesFonctionnalites() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Fonctionnalite modifierFonctionnalite(@NonNull @PathVariable String id, @NonNull @RequestBody Fonctionnalite fonctionnalite) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerFonctionnalite(@NonNull @PathVariable String id) throws ParcoursException;

    /**
     * Réordonne les fonctionnalités selon la liste ordonnée d'ids fournie.
     * La renumérotation est atomique (ordre 1, 2, 3… sans trous).
     */
    @PutMapping(path = "/reorder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Fonctionnalite> reordonnerFonctionnalites(@NonNull @RequestBody ReorderRequest request) throws ParcoursException;
}
