package cmr.notep.config.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MapperBean {
    @Bean
    public DozerBeanMapper getDozerMapper() {
        DozerBeanMapper dozerMapper = new DozerBeanMapper();
        dozerMapper.setMappingFiles(Arrays.asList("dozerEntityMapping.xml"));
        dozerMapper.setCustomConverters(Arrays.asList(new PersonnesConverter(dozerMapper)));
        return dozerMapper;
    }
}
