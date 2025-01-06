package cmr.notep.api;


import cmr.notep.modele.Ressources;
import cmr.notep.modele.RessourcesRequestBuilder;
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
    Ressources avoirRessource (@NonNull @RequestParam(name="idRessource") String idRessource);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Ressources> avoirToutRessources();

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/sortRessorces/"
    )
    List<Ressources> triRessources(@RequestBody RessourcesRequestBuilder ressourcesRequestBuilder);

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
