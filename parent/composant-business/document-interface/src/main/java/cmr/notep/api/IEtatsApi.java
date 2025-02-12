package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Etats;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("etats")
public interface IEtatsApi {
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Etats posterEtat(@NonNull @RequestBody Etats Etat);

    @GetMapping(
            path = "/{idEtat}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Etats avoirEtat(@NonNull @RequestParam(name = "idEtat") String idEtat) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Etats> avoirTousEtats();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerEtat(@NonNull @RequestBody Etats Etat);


}
