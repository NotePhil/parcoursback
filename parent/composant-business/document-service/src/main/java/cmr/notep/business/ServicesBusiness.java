package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.ServicesEntity;
import cmr.notep.modele.Services;
import cmr.notep.repository.ServicesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ServicesBusiness {
    private final DaoAccessorService daoAccessorService ;

    public ServicesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public Services avoirService(String idService) throws Throwable {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ServicesRepository.class)
                .findById(idService)
                .orElseThrow(()-> new RuntimeException("Service introuvable")), Services.class);
    }

    public List<Services> avoirToutServices() {
        return daoAccessorService.getRepository(ServicesRepository.class).findAll()
                .stream().map(tache -> dozerMapperBean.map(tache, Services.class))
                .collect(Collectors.toList());
    }

    public void supprimerService(Services tache)
    {
        daoAccessorService.getRepository(ServicesRepository.class)
                .deleteById(tache.getId().toString());
    }

    public Services posterService(Services tache) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(ServicesRepository.class)
                .save(dozerMapperBean.map(tache, ServicesEntity.class)), Services.class );
    }
}
