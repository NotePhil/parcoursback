package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Exemplaires;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("exemplaires")
public interface IExemplairesApi {

    @GetMapping(
            path = "/{idCompte}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Exemplaires avoirExemplaire (@NonNull @RequestParam(name="idExemplaire") String idExemplaire) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Exemplaires> avoirToutExemplaires();

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Exemplaires posterExemplaires(@NonNull @RequestBody Exemplaires exemplaires) ;

}
