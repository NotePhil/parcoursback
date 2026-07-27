package cmr.notep.login.clientws;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.config.LoginConfig;
import cmr.notep.modele.Personnels;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Client WS vers le service Personnel (document-service).
 * Utilisé uniquement quand parcours.gateway.mode=ws.
 */
@Component
public class PersonnelClientWs implements IPersonnelsApi {

    public static final String URI_PERSONNELS = "/personnels/";
    private final IGenericWsClientApi genericWsClientApi;
    private final LoginConfig loginConfig;

    public PersonnelClientWs(IGenericWsClientApi genericWsClientApi, LoginConfig loginConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.loginConfig = loginConfig;
    }

    @Override
    public Personnels posterPersonnel(Personnels personnel) {
        return null;
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(loginConfig.getUrlPersonnelApiService() + URI_PERSONNELS + idPersonnel)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Personnels.class);
        throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR,
                "Erreur récupération personnel " + idPersonnel + " - http: " + response.getCode());
    }

    @Override
    public List<Personnels> avoirTousPersonnels() {
        return List.of();
    }

    @Override
    public void SupprimerPersonnel(Personnels personnel) {
    }
}
