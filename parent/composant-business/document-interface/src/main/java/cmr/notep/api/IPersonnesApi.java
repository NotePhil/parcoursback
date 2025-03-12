package cmr.notep.api;

import cmr.notep.modele.MacroPersonnes;
import cmr.notep.modele.Personnes;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("personnes")
public interface IPersonnesApi {

    @GetMapping(
            path = "/find",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Personnes avoirPersonne (@NonNull @RequestParam(name="idPersonnes") String idPersonnes);

    @GetMapping(
            path = "/find_macro/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MacroPersonnes> avoirParElemnt (@NonNull @RequestParam(name="value") String value);


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Personnes> avoirToutPersonnes();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerPersonne(@NonNull @RequestBody Personnes Personnes);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Personnes posterPersonne(@NonNull @RequestBody Personnes Personnes) ;

}
