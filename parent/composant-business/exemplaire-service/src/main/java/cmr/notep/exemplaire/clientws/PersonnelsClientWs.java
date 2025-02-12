package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Personnels;
import cmr.notep.modele.Ressources;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!ittest")
public class PersonnelsClientWs implements IPersonnelsApi {
    public static final String URI_PERSONNELS = "/personnels/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public PersonnelsClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public Personnels posterPersonnel(Personnels Personnel) {
        return null;
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_PERSONNELS + idPersonnel)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Personnels.class);
        throw new ParcoursException("Erreur lors de la récupération  " + idPersonnel + " code http : " + response.getReponse() + " reponse : " + response.getReponse());

    }

    @Override
    public List<Personnels> avoirTousPersonnels() {
        return null;
    }

    @Override
    public void SupprimerPersonnel(Personnels personnel) {

    }
}
