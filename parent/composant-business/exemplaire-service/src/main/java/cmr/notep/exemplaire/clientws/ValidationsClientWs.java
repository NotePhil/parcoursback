package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IValidationsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Etats;
import cmr.notep.modele.Validations;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Profile("!ittest")
public class ValidationsClientWs implements IValidationsApi {
    public static final String URI_VALIDATIONS = "/validations/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public ValidationsClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }


    @Override
    public Validations posterValidation(Validations Validation) {
        return null;
    }

    @Override
    public Validations avoirValidation(String idValidation) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_VALIDATIONS + idValidation)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Validations.class);
        throw new ParcoursException("Erreur lors de la récupération  " + idValidation + " code http : " + response.getReponse() + " reponse : " + response.getReponse());

    }

    @Override
    public List<Validations> avoirTousValidations() {
        return null;
    }

    @Override
    public void SupprimerValidation(Validations Validation) {

    }
}
