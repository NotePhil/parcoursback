package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnels;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("personnels")
public interface IPersonnelsApi {


    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Personnels posterPersonnel(@NonNull @RequestBody Personnels Personnel);

    @GetMapping(
            path = "/{idPersonnel}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Personnels avoirPersonnel(@NonNull @RequestParam(name = "idPersonnel") String idPersonnel) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Personnels> avoirTousPersonnels();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerPersonnel(@NonNull @RequestBody Personnels personnel);

}
