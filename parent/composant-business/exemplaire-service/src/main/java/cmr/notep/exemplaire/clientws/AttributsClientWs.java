package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IAttributsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Personnes;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!ittest")
public class AttributsClientWs implements IAttributsApi {
    public static final String URI_ATTRIBUTS = "/attributs/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public AttributsClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public Attributs avoirAttribut(String idAttribut) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_ATTRIBUTS + idAttribut)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Attributs.class);
        throw new ParcoursException("Erreur lors de la récupération  " + idAttribut + " code http : " + response.getReponse() + " reponse : " + response.getReponse());
    }

    @Override
    public List<Attributs> avoirToutAttribut() {
        return null;
    }

    @Override
    public void supprimerAttribut(Attributs attributs) {

    }

    @Override
    public Attributs posterAttribut(Attributs attributs) {
        return null;
    }
}
