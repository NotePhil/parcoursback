package cmr.notep.exemplaire.clientws;

import cmr.notep.api.IEtatsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exemplaire.config.ExemplaireConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Etats;
import cmr.notep.utile.serialiser.JacksonHelper;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Profile("!ittest")
public class EtatsClientWs implements IEtatsApi {
    public static final String URI_ETATS = "/etats/";
    private final IGenericWsClientApi genericWsClientApi;
    private final ExemplaireConfig exemplaireConfig;

    public EtatsClientWs(IGenericWsClientApi genericWsClientApi, ExemplaireConfig exemplaireConfig) {
        this.genericWsClientApi = genericWsClientApi;
        this.exemplaireConfig = exemplaireConfig;
    }

    @Override
    public Etats posterEtat(Etats Etat) {
        return null;
    }

    @Override
    public Etats avoirEtat(String idEtat) throws ParcoursException {
        GenericWsRequest request = GenericWsRequest.builder()
                .url(exemplaireConfig.getUrlDocumentApiService() + URI_ETATS + idEtat)
                .method("GET")
                .build();
        GenericWsResponse response = genericWsClientApi.sendRequest(request);
        if (response.getCode() == 200)
            return JacksonHelper.objetFromJson(response.getReponse(), Etats.class);
        throw new ParcoursException("Erreur lors de la récupération  " + idEtat + " code http : " + response.getReponse() + " reponse : " + response.getReponse());

    }

    @Override
    public List<Etats> avoirTousEtats() {
        return null;
    }

    @Override
    public void SupprimerEtat(Etats Etat) {

    }
}
