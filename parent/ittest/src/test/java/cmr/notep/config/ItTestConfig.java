package cmr.notep.config;

import cmr.notep.DocumentApplication;
import cmr.notep.ExemplaireApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"cmr.notep"},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                DocumentApplication.class,
                ExemplaireApplication.class
            })
        }
)
@EntityScan(basePackages = {"cmr.notep"})
@Profile("ittest")
public class ItTestConfig {
}
