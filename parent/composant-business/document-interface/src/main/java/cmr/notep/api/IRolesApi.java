package cmr.notep.api;

import cmr.notep.modele.Role;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("role")
public interface IRolesApi {
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Role posterRole(@NonNull @RequestBody Role Role);

    @GetMapping(
            path = "/{idRole}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Role avoirRole(@NonNull @RequestParam(name = "idRole") String idRole);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Role> avoirTousRoles();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerRole(@NonNull @RequestBody Role Role);


}
