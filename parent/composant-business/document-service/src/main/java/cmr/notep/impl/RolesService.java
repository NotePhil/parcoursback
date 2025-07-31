package cmr.notep.impl;

import cmr.notep.api.IRolesApi;
import cmr.notep.business.RolesBusiness;
import cmr.notep.modele.Role;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Transactional
public class RolesService implements IRolesApi {

    private final RolesBusiness rolesBusiness;

    public RolesService(RolesBusiness rolesBusiness) {
        this.rolesBusiness = rolesBusiness;
    }

    @Override
    public Role posterRole(Role Role) {
        return rolesBusiness.posterRole(Role);
    }

    @Override
    public Role avoirRole(String idRole) {
        return rolesBusiness.avoirRole(idRole);
    }

    @Override
    public List<Role> avoirTousRoles() {
        System.out.print("tester avoir role");
        return rolesBusiness.avoirToutRoles();
    }

    @Override
    public void SupprimerRole(Role Role) {
        rolesBusiness.supprimerRole(Role);
    }
}
