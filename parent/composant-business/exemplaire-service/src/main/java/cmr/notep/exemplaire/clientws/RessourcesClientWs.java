package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IRessourcesApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
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
public class RessourcesClientWs implements IRessourcesApi {
    public static final String URI_RESSOURCES = "/ressources/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public RessourcesClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public Ressources avoirRessource(String idRessource) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_RESSOURCES + idRessource)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Ressources.class);
        throw new ParcoursException("Erreur lors de la récupération  " + idRessource + " code http : " + response.getReponse() + " reponse : " + response.getReponse());
    }

    @Override
    public List<Ressources> avoirToutRessources() {
        return null;
    }

    @Override
    public void supprimerRessources(Ressources ressources) {

    }

    @Override
    public Ressources posterRessources(Ressources ressources) {
        return null;
    }
}
