package cmr.notep.impl;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.business.JouerRolesBusiness;
import cmr.notep.business.PersonnelsBusiness;
import cmr.notep.modele.Personnels;
import cmr.notep.modele.Roles;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class PersonnelsService implements IPersonnelsApi {

    private final PersonnelsBusiness personnelsBusiness;
    private final JouerRolesBusiness jouerRolesBusiness;

    public PersonnelsService(PersonnelsBusiness personnelsBusiness, JouerRolesBusiness JouerRolesBusiness) {
        this.personnelsBusiness = personnelsBusiness;
        this.jouerRolesBusiness = JouerRolesBusiness;
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
    public void assignerRoleAuPersonnel(@NonNull @RequestBody String Personnel ,
                                 @NonNull @RequestBody String roles){
        jouerRolesBusiness.assignerRoleAuPersonnel(Personnel , roles) ;
    }

    @Override
    public void SupprimerPersonnel(Personnels personnel) {
        personnelsBusiness.supprimerPersonnel(personnel);
    }
}
