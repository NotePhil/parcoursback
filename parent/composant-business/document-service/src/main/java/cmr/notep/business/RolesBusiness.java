package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.RolesEntity;
import cmr.notep.modele.Role;
import cmr.notep.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class RolesBusiness {

    private final DaoAccessorService daoAccessorService;

    public RolesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public Role avoirRole(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(RolesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("role inexistant")), Role.class);
    }

    public List<Role> avoirToutRoles() {
        return daoAccessorService.getRepository(RolesRepository.class).findAll()
                .stream().map(role ->dozerMapperBean.map(role, Role.class))
                .collect(Collectors.toList());
    }

    public void supprimerRole(Role Role)
    {
        daoAccessorService.getRepository(RolesRepository.class)
                .deleteById(Role.getId().toString());
    }

    public Role posterRole(Role Role) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(RolesRepository.class)
                .save(dozerMapperBean.map(Role, RolesEntity.class)), Role.class);
    }
}
