package cmr.notep.api;


import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.PrecoMouvements;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("precomouvements")
public interface IPrecoMouvementsApi {
    @GetMapping(
            path = "/{idPrecoMouvements}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    PrecoMouvements avoirPrecoMouvement (@NonNull @RequestParam(name="idPrecomouvements") String idPrecomouvements) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<PrecoMouvements> avoirToutPrecoMouvement();

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void supprimerPrecoMouvements(@NonNull @RequestBody PrecoMouvements precoMouvements);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    PrecoMouvements posterPrecoMouvements(@NonNull @RequestBody PrecoMouvements precoMouvements) ;
}
