package cmr.notep.impl;

import cmr.notep.api.IParcoursApi;
import cmr.notep.business.ParcoursBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Parcours;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ParcoursService implements IParcoursApi {

    private final ParcoursBusiness parcoursBusiness;

    public ParcoursService(ParcoursBusiness parcoursBusiness) {
        this.parcoursBusiness = parcoursBusiness;
    }

    @Override
    public Parcours avoirParcour(String idparcour) throws ParcoursException {
        return this.parcoursBusiness.avoirParcour(idparcour);
    }

    @Override
    public List<Parcours> avoirToutesparcour() {
        return this.parcoursBusiness.avoirToutesparcour();
    }

    @Override
    public void supprimerparcour(Parcours parcours) {

    }

    @Override
    public Parcours posterparcours(Parcours parcours) {
        return this.parcoursBusiness.posterparcours(parcours);
    }
}
