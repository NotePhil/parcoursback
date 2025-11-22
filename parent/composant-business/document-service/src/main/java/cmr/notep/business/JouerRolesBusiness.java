package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.JouerRolesEntity;
import cmr.notep.dao.PersonnelsEntity;
import cmr.notep.dao.RolesEntity;
import cmr.notep.repository.JouerRolesRepository;
import cmr.notep.repository.PersonnelsRepository;
import cmr.notep.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
@Transactional
public class JouerRolesBusiness {

    private final DaoAccessorService daoAccessorService;

    public JouerRolesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public void assignerRoleAuPersonnel(String personnelId, String roleId) {

        PersonnelsEntity personnel = this.daoAccessorService.getRepository(PersonnelsRepository.class).findById(personnelId).orElseThrow();
        RolesEntity role = this.daoAccessorService.getRepository(RolesRepository.class).findById(roleId).orElseThrow();

        JouerRolesEntity jouerRole = new JouerRolesEntity();
        jouerRole.setPersonnelsEntity(personnel);
        jouerRole.setRolesEntity(role);
        jouerRole.setDateCreation(new Date());
        jouerRole.setEtat(true);

        this.daoAccessorService.getRepository(JouerRolesRepository.class).save(jouerRole);

        personnel.getJouerRolesEntities().add(jouerRole);
    }

}
