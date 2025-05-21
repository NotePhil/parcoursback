package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Actions;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("actions")
public interface IActionsApi {

    @GetMapping(
            path = "/{idaction}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Actions avoirAction (@NonNull @PathVariable(name = "idaction") String idaction) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Actions> avoirToutesActions();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerActions(@NonNull @RequestBody Actions actions);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Actions posterAction(@NonNull @RequestBody Actions actions);

}
