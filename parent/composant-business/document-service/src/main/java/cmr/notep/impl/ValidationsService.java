package cmr.notep.impl;

import cmr.notep.api.IValidationsApi;
import cmr.notep.business.ValidationsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Validations;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class ValidationsService implements IValidationsApi {

    private final ValidationsBusiness validationsBusiness;

    public ValidationsService(ValidationsBusiness validationsBusiness) {
        this.validationsBusiness = validationsBusiness;
    }

    @Override
    public Validations posterValidation(Validations validation) {
        return this.validationsBusiness.posterValidation(validation);
    }

    @Override
    public Validations avoirValidation(String idValidation) throws ParcoursException {
        return this.avoirValidation(idValidation);
    }

    @Override
    public List<Validations> avoirTousValidations() {
        return this.validationsBusiness.avoirToutValidations();
    }

    @Override
    public void SupprimerValidation(Validations Validation) {
        this.validationsBusiness.supprimerValidation(Validation);
    }
}
