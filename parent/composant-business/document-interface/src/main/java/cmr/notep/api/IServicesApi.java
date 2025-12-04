package cmr.notep.api;

import cmr.notep.modele.Services;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("services")
public interface IServicesApi {
    @GetMapping(
            path = "/{idService}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )Services avoirServices (@NonNull @PathVariable(name="idService") String idService) throws Throwable;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    List<Services> avoirToutServices();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerServices(@NonNull @RequestBody Services service);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Services PosterServices(@NonNull @RequestBody Services service) ;
}
