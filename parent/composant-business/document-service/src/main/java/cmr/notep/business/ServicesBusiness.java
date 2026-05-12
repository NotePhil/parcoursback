package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.ServicesEntity;
import cmr.notep.modele.Services;
import cmr.notep.repository.ServicesRepository;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class ServicesBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;

    @PersistenceContext
    private EntityManager entityManager;

    public ServicesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
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

    public Services posterService(Services tache) throws ParcoursException {
            ServicesEntity saved = this.daoAccessorService.getRepository(ServicesRepository.class)
                    .save(dozerMapperBean.map(tache, ServicesEntity.class));
            return dozerMapperBean.map(saved, Services.class);
    }
}
