package cmr.notep.login.traduction;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.config.LoginConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeepLTraductionServiceTest {

    @Mock RestTemplate restTemplate;
    @Mock LoginConfig loginConfig;
    @InjectMocks DeepLTraductionService traductionService;

    @BeforeEach
    void setup() {
        when(loginConfig.getUrlTraductionApiService()).thenReturn("https://api-free.deepl.com/v2/translate");
        when(loginConfig.getTraductionApiKey()).thenReturn("test-key");
    }

    @Test
    void traduire_devraitRetournerTexteOriginelSiVide() throws ParcoursException {
        assertThat(traductionService.traduire("", "EN")).isEmpty();
        assertThat(traductionService.traduire(null, "EN")).isNull();
    }

    @Test
    void traduire_devraitRetournerTexteTraduiteViaDeepL() throws ParcoursException {
        String reponseJson = "{\"translations\":[{\"detected_source_language\":\"FR\",\"text\":\"Create\"}]}";
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(reponseJson));

        String result = traductionService.traduire("Créer", "EN");

        assertThat(result).isEqualTo("Create");
    }

    @Test
    void traduire_devraitRetournerOriginalSiStatutNonOk() throws ParcoursException {
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));

        String result = traductionService.traduire("Créer", "EN");

        assertThat(result).isEqualTo("Créer");
    }

    @Test
    void traduire_devraitLeverParcoursExceptionSiErreurReseau() {
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Connection refused"));

        assertThatThrownBy(() -> traductionService.traduire("Bonjour", "EN"))
                .isInstanceOf(ParcoursException.class)
                .hasMessageContaining("Bonjour");
    }
}
