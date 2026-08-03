package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.DocEtats;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("docEtats")
public interface IDocEtatsApi {
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    DocEtats posterDocEtat(@NonNull @RequestBody DocEtats docEtat);

    @GetMapping(
            path = "/{idDocEtat}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    DocEtats avoirDocEtat(@NonNull @PathVariable(name = "idDocEtat") String idDocEtat) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<DocEtats> avoirTousDocEtats();

    @GetMapping(
            path = "/document/{idDocument}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<DocEtats> listeDocEtatsParDocument(@NonNull @PathVariable(name = "idDocument") String idDocument);
    @DeleteMapping(
            path = "/{idDocEtat}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerDocEtat(@NonNull @RequestBody DocEtats docEtat);


}
