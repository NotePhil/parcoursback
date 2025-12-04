package cmr.notep.api;

import cmr.notep.modele.Roles;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("roles")
public interface IRolesApi {
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Roles posterRole(@NonNull @RequestBody Roles Role);

    @GetMapping(
            path = "/{idRole}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Roles avoirRole(@NonNull @PathVariable(name = "idRole") String idRole);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Roles> avoirTousRoles();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerRole(@NonNull @RequestBody Roles Role);


}
