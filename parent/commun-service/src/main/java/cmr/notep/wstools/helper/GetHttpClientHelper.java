package cmr.notep.wstools.helper;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class GetHttpClientHelper {
    @Cacheable(cacheNames = "httpClient", key = "#urlPrefixe")
    public HttpClient getHttpClient(String urlPrefixe) {
        return HttpClient.newHttpClient();
    }
}
