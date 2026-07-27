package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.RoleEntity;
import cmr.notep.login.modele.Role;
import cmr.notep.login.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class RoleBusiness {

    private final RoleRepository roleRepository;

    public RoleBusiness(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role posterRole(Role role) throws ParcoursException {
        RoleEntity entity = dozerMapperBean.map(role, RoleEntity.class);
        return dozerMapperBean.map(roleRepository.save(entity), Role.class);
    }

    public Role avoirRole(String id) throws ParcoursException {
        return roleRepository.findById(id)
                .map(e -> dozerMapperBean.map(e, Role.class))
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Role introuvable : " + id));
    }

    public List<Role> avoirTousRoles() {
        return roleRepository.findAll().stream()
                .map(e -> dozerMapperBean.map(e, Role.class))
                .toList();
    }

    public Role modifierRole(String id, Role role) throws ParcoursException {
        RoleEntity entity = roleRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Role introuvable : " + id));
        entity.setTitre(role.getTitre());
        entity.setDescription(role.getDescription());
        entity.setEtat(role.getEtat());
        return dozerMapperBean.map(roleRepository.save(entity), Role.class);
    }

    public void supprimerRole(String id) throws ParcoursException {
        if (!roleRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Role introuvable : " + id);
        roleRepository.deleteById(id);
    }
}
