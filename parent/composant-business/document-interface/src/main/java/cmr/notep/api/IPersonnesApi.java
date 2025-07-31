package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PersonnesPhysique;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("personnes")
public interface IPersonnesApi {

    @GetMapping(
            path = "/{idPersonne}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Personnes avoirPersonne (@NonNull @RequestParam(name="idPersonne") String idPersonne) throws ParcoursException;


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Personnes> avoirToutPersonnes();

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Personnes posterPersonne(@NonNull @RequestBody Personnes Personnes) ;

    @GetMapping(
            path = "/personnesphysique",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<PersonnesPhysique> avoirToutPatient();

    @GetMapping(
            path = "/personnesphysique/{idPersonne}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    PersonnesPhysique avoirPatient (@NonNull @RequestParam(name="idPersonne") String idPersonne) throws ParcoursException;


}
