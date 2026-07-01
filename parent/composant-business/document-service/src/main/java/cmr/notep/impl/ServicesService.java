package cmr.notep.impl;


import cmr.notep.api.IServicesApi;
import cmr.notep.business.ServicesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Services;
import lombok.NonNull;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
//@Transactional
public class ServicesService implements IServicesApi {

    private final ServicesBusiness servicesBusiness ;

    public ServicesService(ServicesBusiness servicesBusiness) {
        this.servicesBusiness = servicesBusiness;
    }

    @Override
    public Services avoirServices(@NonNull String idService) throws Throwable {
        return servicesBusiness.avoirService(idService);
    }

    @Override
    public List<Services> avoirToutServices() {
        return servicesBusiness.avoirToutServices();
    }

    @Override
    public void supprimerServices(Services service) {
        servicesBusiness.supprimerService(service);
    }

    @Override
    public Services PosterServices(Services service) throws ParcoursException {
      try {
            return servicesBusiness.posterService(service);
      }
     catch (DataIntegrityViolationException e) {
        throw new ParcoursException(ParcoursExceptionCodeEnum.DUPLICATE_KEY, "la valeur d'une clé dupliquée");
      }
    }
}
