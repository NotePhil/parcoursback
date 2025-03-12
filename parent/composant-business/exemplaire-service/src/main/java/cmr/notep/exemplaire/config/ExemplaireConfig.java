package cmr.notep.exemplaire.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cmr.notep.exemplaire.repository")
public class ExemplaireConfig {


    @Value("${url.document.api.service:http://localhost:8080/document}")
    private String urlDocumentApiService;
    public static DozerBeanMapper dozerMapperBean;
    @PostConstruct
    void init(){
        dozerMapperBean = new DozerBeanMapper();

    }
    public String getUrlDocumentApiService() {
        return urlDocumentApiService;
    }
}
