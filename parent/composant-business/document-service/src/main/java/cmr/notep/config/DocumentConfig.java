package cmr.notep.config;

import jakarta.annotation.PostConstruct;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cmr.notep.repository")
public class DocumentConfig {
    public static DozerBeanMapper dozerMapperBean;
    public static DozerBeanMapper dozerMapperBeanEntity;
    @PostConstruct
    void init(){
        dozerMapperBean = new DozerBeanMapper();
        dozerMapperBeanEntity = new DozerBeanMapper(Collections.singletonList("dozerEntityMapping.xml"));
    }

}
