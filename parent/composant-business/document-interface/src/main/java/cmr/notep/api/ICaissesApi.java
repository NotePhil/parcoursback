package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Caisses;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("caisses")
public interface ICaissesApi {

    @GetMapping(
            path = "/{idCaisse}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Caisses avoirCaisse (@NonNull @PathVariable(name = "idCaisse") String idCaisse) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Caisses> avoirToutCaisses();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerCaisse(@NonNull @RequestBody Caisses caisse);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Caisses posterCaisse(@NonNull @RequestBody Caisses caisse);
}
