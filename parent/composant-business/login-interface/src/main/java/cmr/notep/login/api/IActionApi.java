package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Action;
import cmr.notep.login.modele.ReorderRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/actions")
public interface IActionApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Action posterAction(@NonNull @RequestBody Action action) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Action avoirAction(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Action> avoirToutesActions() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Action modifierAction(@NonNull @PathVariable String id, @NonNull @RequestBody Action action) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerAction(@NonNull @PathVariable String id) throws ParcoursException;

    @PutMapping(path = "/reorder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Action> reordonnerActions(@NonNull @RequestBody ReorderRequest request) throws ParcoursException;
}
