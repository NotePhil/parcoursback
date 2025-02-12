package cmr.notep.wstools.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.wstools.api.IGenericWsClientApi;
import cmr.notep.wstools.helper.GenericClientWsHelper;
import cmr.notep.wstools.helper.GetHttpClientHelper;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GenericWsClientImpl implements IGenericWsClientApi {
    private final GetHttpClientHelper getHttpClientHelper;

    public GenericWsClientImpl(GetHttpClientHelper getHttpClientHelper) {
        this.getHttpClientHelper = getHttpClientHelper;
    }
    //TODO Test unitaire à faire
    @Override
    public GenericWsResponse sendRequest(GenericWsRequest request) throws ParcoursException {
        HttpClient client = GenericClientWsHelper.calculCleEtGetHttpClient(request.getUrl(), getHttpClientHelper);
        HttpRequest requete = GenericClientWsHelper.ajoutConfigurationRequete(request);
        try {
           return GenericClientWsHelper.construireReponse(client.send(requete, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException e) {
            throw new ParcoursException("IOException lors de l'appel ws " + request.getUrl(), e);
        } catch (InterruptedException e) {
            throw new ParcoursException("InterruptedException lors de l'appel ws " + request.getUrl(), e);
        }
    }
}
