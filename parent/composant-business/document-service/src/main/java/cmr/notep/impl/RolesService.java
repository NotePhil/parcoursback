package cmr.notep.impl;

import cmr.notep.api.IRolesApi;
import cmr.notep.business.RolesBusiness;
import cmr.notep.modele.AssignValidationRoleRequest;
import cmr.notep.modele.Roles;
import cmr.notep.modele.Validations;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class RolesService implements IRolesApi {

    private final RolesBusiness rolesBusiness;

    public RolesService(RolesBusiness rolesBusiness) {
        this.rolesBusiness = rolesBusiness;
    }

    @Override
    public Roles posterRole(Roles Role) {
        return rolesBusiness.posterRole(Role);
    }

    @Override
    public Roles avoirRole(String idRole) {
        return rolesBusiness.avoirRole(idRole);
    }

    @Override
    public List<Roles> avoirTousRoles() {
        System.out.print("tester avoir role");
        return rolesBusiness.avoirToutRoles();
    }

    @Override
    public void SupprimerRole(Roles Role) {
        rolesBusiness.supprimerRole(Role);
    }

    @Override
    public Roles assignValidationRole(AssignValidationRoleRequest assignValidationRoleRequest) {
        return rolesBusiness.assignValidationRole(
                assignValidationRoleRequest.getValidation(),  assignValidationRoleRequest.getRole());
    }
}
