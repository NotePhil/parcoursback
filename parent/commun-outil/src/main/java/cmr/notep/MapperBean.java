package cmr.notep;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MapperBean {
    DozerBeanMapper dozerMapper;
    @PostConstruct
    void init(){

    }
}
