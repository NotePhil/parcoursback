package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Distributeurs;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("distributeurs")
public interface IDistributeursApi {
    @GetMapping(
            path = "/{idDistributeurs}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Distributeurs avoirDistributeur (@NonNull @RequestParam(name="idDistributeurs") String idDistributeurs) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Distributeurs> avoirToutDistributeurs();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerDistributeur(@NonNull @RequestBody Distributeurs Distributeurs);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Distributeurs posterDistributeur(@NonNull @RequestBody Distributeurs Distributeurs) ;

}
