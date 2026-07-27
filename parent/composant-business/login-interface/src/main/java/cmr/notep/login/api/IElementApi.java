package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Element;
import cmr.notep.login.modele.ReorderRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/elements")
public interface IElementApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Element posterElement(@NonNull @RequestBody Element element) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Element avoirElement(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Element> avoirTousElements() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Element modifierElement(@NonNull @PathVariable String id, @NonNull @RequestBody Element element) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerElement(@NonNull @PathVariable String id) throws ParcoursException;

    @PutMapping(path = "/reorder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Element> reordonnerElements(@NonNull @RequestBody ReorderRequest request) throws ParcoursException;
}
