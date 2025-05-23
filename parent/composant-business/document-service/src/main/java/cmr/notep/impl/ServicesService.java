package cmr.notep.impl;


import cmr.notep.api.IServicesApi;
import cmr.notep.business.ServicesBusiness;
import cmr.notep.modele.Services;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
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
    public Services PosterServices(Services service) {
        return servicesBusiness.posterService(service);
    }
}
