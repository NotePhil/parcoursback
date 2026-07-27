package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IRoleApi;
import cmr.notep.login.business.RoleBusiness;
import cmr.notep.login.modele.Role;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class RoleService implements IRoleApi {

    private final RoleBusiness roleBusiness;

    public RoleService(RoleBusiness roleBusiness) {
        this.roleBusiness = roleBusiness;
    }

    @Override public Role posterRole(Role role) throws ParcoursException { return roleBusiness.posterRole(role); }
    @Override public Role avoirRole(String id) throws ParcoursException { return roleBusiness.avoirRole(id); }
    @Override public List<Role> avoirTousRoles() throws ParcoursException { return roleBusiness.avoirTousRoles(); }
    @Override public Role modifierRole(String id, Role role) throws ParcoursException { return roleBusiness.modifierRole(id, role); }
    @Override public void supprimerRole(String id) throws ParcoursException { roleBusiness.supprimerRole(id); }
}
