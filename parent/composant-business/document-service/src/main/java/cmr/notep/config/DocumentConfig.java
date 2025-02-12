package cmr.notep.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cmr.notep.repository")
public class DocumentConfig {
    public static DozerBeanMapper dozerMapperBean;
    @PostConstruct
    void init(){
        dozerMapperBean = new DozerBeanMapper();
    }

}
