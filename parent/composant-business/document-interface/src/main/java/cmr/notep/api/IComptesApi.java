package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Comptes;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comptes")
public interface IComptesApi {

    @GetMapping(
            path = "/{idCompte}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Comptes avoirCompte (@NonNull @RequestParam(name="idCompte") String idCompte) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Comptes> avoirToutComptes();

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Comptes posterCompte(@NonNull @RequestBody Comptes comptes) ;

}
