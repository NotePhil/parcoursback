package cmr.notep.exemplaire.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.modele.Exemplaires;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("exemplaire/exemplaires")
public interface IExemplairesApi {
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Exemplaires posterExemplaire(@NonNull @RequestBody Exemplaires exemplaire) throws ParcoursException;

    @GetMapping(
            path = "/{idExemplaire}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Exemplaires avoirExemplaire(@NonNull @RequestParam(name = "idExemplaire") String idExemplaire) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Exemplaires> avoirTousExemplaires() throws ParcoursException;
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Boolean SupprimerExemplaire(@NonNull @RequestBody Exemplaires exemplaire);
}
