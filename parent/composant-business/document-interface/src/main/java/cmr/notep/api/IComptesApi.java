package cmr.notep.api;

import cmr.notep.modele.Comptes;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comptes")
public interface IComptesApi {
    @GetMapping(
            path = "/{idComptes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Comptes avoirComptes (@NonNull @RequestParam(name="idComptes") String idComptes);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Comptes> avoirToutComptes();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerComptes(@NonNull @RequestBody Comptes comptes);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Comptes posterComptes(@NonNull @RequestBody Comptes comptes) ;

}