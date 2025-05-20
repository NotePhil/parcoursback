package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Caisses;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("caisses")
public interface ICaisses {

    @GetMapping(
            path = "/{idcaisses}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Caisses avoircaisse (@NonNull @PathVariable(name = "idcaisse") String idcaisse) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Caisses> avoirToutescaisses();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimercaisse(@NonNull @RequestBody Caisses caisse);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Caisses postercaisse(@NonNull @RequestBody Caisses caisse);

}
