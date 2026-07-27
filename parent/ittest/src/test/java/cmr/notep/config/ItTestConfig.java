package cmr.notep.config;

import cmr.notep.DocumentApplication;
import cmr.notep.ExemplaireApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"cmr.notep"}
)
@EntityScan(basePackages = {"cmr.notep"})
@Profile("ittest")
public class ItTestConfig {
}
