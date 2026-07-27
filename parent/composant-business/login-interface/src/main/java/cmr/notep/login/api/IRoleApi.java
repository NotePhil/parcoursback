package cmr.notep.login.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.modele.Role;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("login/roles")
public interface IRoleApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Role posterRole(@NonNull @RequestBody Role role) throws ParcoursException;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Role avoirRole(@NonNull @PathVariable String id) throws ParcoursException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Role> avoirTousRoles() throws ParcoursException;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Role modifierRole(@NonNull @PathVariable String id, @NonNull @RequestBody Role role) throws ParcoursException;

    @DeleteMapping(path = "/{id}")
    void supprimerRole(@NonNull @PathVariable String id) throws ParcoursException;
}
