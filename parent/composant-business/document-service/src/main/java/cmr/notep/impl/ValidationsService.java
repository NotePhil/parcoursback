package cmr.notep.impl;

import cmr.notep.api.IValidationsApi;
import cmr.notep.business.ValidationsBusiness;
import cmr.notep.modele.Validations;
import lombok.NonNull;
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
    public Validations posterValidation(@NonNull Validations validation) {
        return validationsBusiness.posterValidation(validation);
    }

    @Override
    public Validations avoirValidation(@NonNull String idValidation) {
        return validationsBusiness.avoirValidation(idValidation);
    }

    @Override
    public List<Validations> avoirTousValidations() {
        return validationsBusiness.avoirToutValidations();
    }

    @Override
    public void supprimerValidation(@NonNull Validations validation) {
        validationsBusiness.supprimerValidation(validation);
    }
}
