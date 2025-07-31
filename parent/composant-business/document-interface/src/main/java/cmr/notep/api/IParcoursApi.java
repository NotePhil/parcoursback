package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Parcours;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("parcours")
public interface IParcoursApi {

    @GetMapping(
            path = "/{idparcour}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Parcours avoirParcour (@NonNull @PathVariable(name = "idparcour") String idparcour) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Parcours> avoirToutesparcour();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerparcour(@NonNull @RequestBody Parcours parcours);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Parcours posterparcours(@NonNull @RequestBody Parcours parcours);
}
