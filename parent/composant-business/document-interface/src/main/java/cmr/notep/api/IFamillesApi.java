package cmr.notep.api;

import cmr.notep.modele.Familles;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("familles")
public interface IFamillesApi {

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Familles posterFamille(@NonNull @RequestBody Familles familles);

    @GetMapping(
            path = "/{idFamille}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Familles avoirFamille(@NonNull @PathVariable(name = "idFamille") String idFamille);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Familles> avoirTousFamilles();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Boolean SupprimerFamilles(@NonNull @RequestBody Familles familles);
}
