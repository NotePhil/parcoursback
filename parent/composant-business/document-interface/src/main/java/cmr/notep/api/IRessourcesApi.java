package cmr.notep.api;


import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Ressources;
import cmr.notep.modele.Ressources;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("ressources")
public interface IRessourcesApi {
    @GetMapping(
            path = "/{idRessource}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Ressources avoirRessource (@NonNull @PathVariable(name="idRessource") String idRessource) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Ressources> avoirToutRessources();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerRessources(@NonNull @RequestBody Ressources ressources);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Ressources posterRessources(@NonNull @RequestBody Ressources ressources) ;

}
