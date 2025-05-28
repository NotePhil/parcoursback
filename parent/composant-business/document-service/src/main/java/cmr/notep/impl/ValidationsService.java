package cmr.notep.impl;

import cmr.notep.api.IValidationsApi;
import cmr.notep.business.ValidationsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Validations;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class ValidationsService implements IValidationsApi {

    private final ValidationsBusiness validationsBusiness ;

    public ValidationsService(ValidationsBusiness validationsBusiness) {
        this.validationsBusiness = validationsBusiness;
    }

    @Override
    public Validations posterValidation(Validations Validation) {
        return validationsBusiness.posterValidation(Validation);
    }

    @Override
    public Validations avoirValidation(@NonNull String idValidation) throws ParcoursException {
        return validationsBusiness.avoirValidation(idValidation);
    }

    @Override
    public List<Validations> avoirToutesValidations() {
        return validationsBusiness.avoirToutesValidation();
    }

    @Override
    public void SupprimerValidation(Validations Validation) {
        validationsBusiness.SupprimerValidation(Validation);
    }
}
