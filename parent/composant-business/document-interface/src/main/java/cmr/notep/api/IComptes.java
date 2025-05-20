package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Comptes;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comptes")
public interface IComptes {

    @GetMapping(
            path = "/{idcomptes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Comptes avoircompte (@NonNull @PathVariable(name = "idcompte") String idcompte) throws ParcoursException;

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
    Comptes posterComptes(@NonNull @RequestBody Comptes comptes);

}
