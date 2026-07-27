package cmr.notep.login.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cmr.notep.login.repository")
public class LoginConfig {

    @Value("${url.personnel.api.service:http://localhost:8684/documentparcours}")
    private String urlPersonnelApiService;

    @Value("${url.traduction.api.service:https://api-free.deepl.com/v2/translate}")
    private String urlTraductionApiService;

    @Value("${traduction.api.key:}")
    private String traductionApiKey;

    public static DozerBeanMapper dozerMapperBean;

    @PostConstruct
    void init() {
        dozerMapperBean = new DozerBeanMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getUrlPersonnelApiService() {
        return urlPersonnelApiService;
    }

    public String getUrlTraductionApiService() {
        return urlTraductionApiService;
    }

    public String getTraductionApiKey() {
        return traductionApiKey;
    }
}
