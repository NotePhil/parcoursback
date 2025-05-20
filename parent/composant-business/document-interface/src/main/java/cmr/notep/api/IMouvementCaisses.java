package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.MouvementCaisses;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("mouvementciasses")
public interface IMouvementCaisses {

    @GetMapping(
            path = "/{idmouvementcaisse}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementCaisses avoirmouvementcaisse (@NonNull @PathVariable(name = "idmouvementcaisse") String idmouvementcaisse) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MouvementCaisses> avoirToutmouvementcaisse();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimermouvementcaisse(@NonNull @RequestBody MouvementCaisses mouvementcaisse);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementCaisses postermouvementcaisse(@NonNull @RequestBody MouvementCaisses mouvementcaisse);

}
