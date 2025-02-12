package cmr.notep.wstools.helper;

import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GenericClientWsHelper {
    public static HttpClient calculCleEtGetHttpClient(String url,GetHttpClientHelper getHttpClientHelper) {
        String urlPrefixe = url.substring(0, url.indexOf("/", 8));
        return ajoutConfiguration(getHttpClientHelper.getHttpClient(urlPrefixe));
    }
    /**
     * Ajout de configuration à HttpClient comme l'authentification, les timeouts, etc.
     * @param httpClient
     * @return
     */
    static HttpClient ajoutConfiguration(HttpClient httpClient) {
        return httpClient;
    }
    //TODO: Ajouter les headers à la requête dans une méthode, prévoir l'usage des cookies
    public static HttpRequest ajoutConfigurationRequete(GenericWsRequest request) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(request.getUrl()))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(request.getMethod(), HttpRequest.BodyPublishers.ofString(request.getBody()));
        return builder.build();
    }

    public static GenericWsResponse construireReponse(HttpResponse<String> response) {
        return GenericWsResponse.builder()
                .reponse(response.body())
                .code(response.statusCode())
                .headers(response.headers().map())
                .build();
    }
}
