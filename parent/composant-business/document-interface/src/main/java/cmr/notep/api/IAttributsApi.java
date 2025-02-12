package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Attributs;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("attributs")
public interface IAttributsApi {

    @GetMapping(
            path = "/{idAttribut}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Attributs avoirAttribut (@NonNull @RequestParam(name = "idAttribut") String idAttributs) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Attributs> avoirToutAttribut();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerAttribut(@NonNull @RequestBody Attributs attributs);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Attributs posterAttribut(@NonNull @RequestBody Attributs attributs);
}
