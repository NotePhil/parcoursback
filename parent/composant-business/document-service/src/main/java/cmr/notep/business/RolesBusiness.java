package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.RolesEntity;
import cmr.notep.dao.ValidationsEntity;
import cmr.notep.modele.Documents;
import cmr.notep.modele.Etats;
import cmr.notep.modele.Roles;
import cmr.notep.modele.Validations;
import cmr.notep.repository.RolesRepository;
import cmr.notep.repository.ValidationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
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

    public Roles avoirRole(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(RolesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("role inexistante")), Roles.class);
    }

    public List<Roles> avoirToutRoles() {
        return daoAccessorService.getRepository(RolesRepository.class).findAll()
                .stream().map(role ->dozerMapperBean.map(role, Roles.class))
                .collect(Collectors.toList());
    }

    public void supprimerRole(Roles Roles)
    {
        daoAccessorService.getRepository(RolesRepository.class)
                .deleteById(Roles.getId().toString());
    }

    public Roles posterRole(Roles Roles) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(RolesRepository.class)
                .save(dozerMapperBean.map(Roles, RolesEntity.class)), Roles.class);
    }

    public Roles assignValidationRole(Validations validation, Roles role)
    {
        log.debug("[assignValidationRole] assigner une validation à un rôle");

        if (validation == null || role == null) {
            throw new IllegalArgumentException("Validation et Rôle doivent être non nuls");
        }

        ValidationsEntity validationEntity = this.daoAccessorService.getRepository(ValidationsRepository.class)
                .findById(validation.getId()).orElse(null);
        RolesEntity roleEntity = this.daoAccessorService.getRepository(RolesRepository.class)
                .findById(role.getId()).orElse(null);

        if (validationEntity == null) {
            throw new RuntimeException("Validation non trouvée en base : " + validation.getId());
        }
        if (roleEntity == null) {
            throw new RuntimeException("Rôle non trouvé en base : " + role.getId());
        }

        boolean exists = roleEntity.getValidationsEntities().stream()
                .anyMatch(v -> v.getId().equals(validationEntity.getId()));
        if (exists) {
            log.debug("[assignValidationRole] Validation déjà assignée au rôle");
            return dozerMapperBean.map(roleEntity, Roles.class);
        }

        validationEntity.setRoleEntity(roleEntity);

        roleEntity.getValidationsEntities().add(validationEntity);

        RolesEntity savedRole = this.daoAccessorService.getRepository(RolesRepository.class).save(roleEntity);

        return dozerMapperBean.map(savedRole, Roles.class);
    }
}
