package cmr.notep.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DaoAccessorService {
    @Autowired
    protected ApplicationContext applicationContext;

    private Map<String, JpaRepository> jpaRepositories = new ConcurrentHashMap<>();
    @PostConstruct
    protected void init() {
        jpaRepositories = applicationContext.getBeansOfType(JpaRepository.class);
    }

    public <T> T getRepository(Class<T> clazz) {
        //TODO adaptation du code à refaire
        //TODO un problème avec le repository.getClass qui ne remonte pas le nom de l'interface JPA mais plutot le proxy d*où un NPE à lors du get
        String keyName = clazz.getSimpleName();
        for (String keyVal: jpaRepositories.keySet()) {
            if(keyVal.equalsIgnoreCase(keyName)){
                keyName = keyVal;
                break;
            }
        }
        return (T) jpaRepositories.get(keyName);
    }
}
