package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IDistributeursApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Distributeurs;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Profile("!ittest")
public class DistributeursClientWs implements IDistributeursApi {
    public static final String URI_DISTRIBUTEURS = "/distributeurs/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public DistributeursClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public Distributeurs avoirDistributeur(String idDistributeur) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_DISTRIBUTEURS + idDistributeur)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Distributeurs.class);
        throw new ParcoursException("Erreur lors de la récupération du document " + idDistributeur + " code http : " + response.getReponse() + " reponse : " + response.getReponse());
    }

    @Override
    public List<Distributeurs> avoirToutDistributeurs() {
        return null;
    }

    @Override
    public void supprimerDistributeur(Distributeurs Distributeurs) {

    }

    @Override
    public Distributeurs posterDistributeur(Distributeurs Distributeurs) {
        return null;
    }
}
