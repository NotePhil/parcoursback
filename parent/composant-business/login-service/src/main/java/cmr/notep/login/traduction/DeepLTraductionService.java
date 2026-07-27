package cmr.notep.login.traduction;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.config.LoginConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implémentation du service de traduction via l'API DeepL Free.
 * URL : https://api-free.deepl.com/v2/translate
 * Langue source fixée à FR (langue de stockage en base).
 */
@Service
@Slf4j
public class DeepLTraductionService implements TraductionService {

    private static final String LANGUE_SOURCE = "FR";
    private final RestTemplate restTemplate;
    private final LoginConfig loginConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepLTraductionService(RestTemplate restTemplate, LoginConfig loginConfig) {
        this.restTemplate = restTemplate;
        this.loginConfig = loginConfig;
    }

    @Override
    public String traduire(String texte, String langueCible) throws ParcoursException {
        if (texte == null || texte.isBlank()) return texte;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "DeepL-Auth-Key " + loginConfig.getTraductionApiKey());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("text", texte);
            body.add("source_lang", LANGUE_SOURCE);
            body.add("target_lang", langueCible.toUpperCase());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    loginConfig.getUrlTraductionApiService(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                return root.path("translations").get(0).path("text").asText();
            }
            log.warn("Traduction échouée pour le texte '{}', retour du texte original", texte);
            return texte;

        } catch (Exception e) {
            log.error("Erreur lors de la traduction : {}", e.getMessage());
            throw new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR,
                    "Erreur appel DeepL pour le texte : " + texte, e);
        }
    }
}
