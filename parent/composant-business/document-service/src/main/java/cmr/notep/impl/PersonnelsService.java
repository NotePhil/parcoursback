package cmr.notep.impl;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.business.PersonnelsBusiness;
import cmr.notep.modele.Personnels;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class PersonnelsService implements IPersonnelsApi {

    private final PersonnelsBusiness personnelsBusiness;

    public PersonnelsService(PersonnelsBusiness personnelsBusiness) {
        this.personnelsBusiness = personnelsBusiness;
    }

    @Override
    public Personnels posterPersonnel(Personnels Personnel) {
        return personnelsBusiness.posterPersonnel(Personnel);
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) {
        return personnelsBusiness.avoirPersonnel(idPersonnel);
    }

    @Override
    public List<Personnels> avoirTousPersonnels() {
        System.out.println("calling avoir tout personnels");
        return personnelsBusiness.avoirToutPersonnels();
    }

    @Override
    public void SupprimerPersonnel(Personnels personnel) {
        personnelsBusiness.supprimerPersonnel(personnel);
    }
}
