package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Validations;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("validations")
public interface IValidationsApi {
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Validations posterValidation(@NonNull @RequestBody Validations Validation);

    @GetMapping(
            path = "/{idValidation}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Validations avoirValidation(@NonNull @RequestParam(name = "idValidation") String idValidation) throws ParcoursException;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Validations> avoirTousValidations();
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void SupprimerValidation(@NonNull @RequestBody Validations Validation);


}
