package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.RolesEntity;
import cmr.notep.dao.ValidationsEntity;
import cmr.notep.dao.RemplirEntity;
import cmr.notep.dao.MissionsEntity;
import cmr.notep.modele.*;
import cmr.notep.repository.RolesRepository;
import cmr.notep.repository.ValidationsRepository;
import cmr.notep.repository.RemplirRepository;
import cmr.notep.repository.MissionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        RolesEntity roleEntity;
        if (Roles.getId() != null) {
            roleEntity = this.daoAccessorService.getRepository(RolesRepository.class)
                    .findById(Roles.getId())
                    .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + Roles.getId()));
            // Mapper les propriétés sauf les relations
            dozerMapperBean.map(Roles, roleEntity);
        } else {
            roleEntity = dozerMapperBean.map(Roles, RolesEntity.class);
        }

        gererMissionRole(Roles, roleEntity);

        RolesEntity savedRole = this.daoAccessorService.getRepository(RolesRepository.class).save(roleEntity);
        return dozerMapperBean.map(savedRole, Roles.class);
    }

    private void gererMissionRole(Roles Roles, RolesEntity roleEntity) {
        // Gérer les missions
        List<Remplir> missions = Roles.getMissions();
        if (missions != null) {
            List<RemplirEntity> existingRemplir = roleEntity.getRemplirEntities() != null ? new ArrayList<>(roleEntity.getRemplirEntities()) : new ArrayList<>();
            List<String> newIds = missions.stream()
                    .filter(m -> m.getId() != null)
                    .map(Remplir::getId)
                    .collect(Collectors.toList());

            // Supprimer les anciens non présents
            for (RemplirEntity re : existingRemplir) {
                if (!newIds.contains(re.getId())) {
                    daoAccessorService.getRepository(RemplirRepository.class).delete(re);
                    roleEntity.getRemplirEntities().remove(re);
                }
            }

            // Ajouter ou mettre à jour
            for (Remplir m : missions) {
                RemplirEntity re;
                if (m.getId() != null) {
                    re = daoAccessorService.getRepository(RemplirRepository.class).findById(m.getId()).orElse(null);
                    if (re == null) {
                        throw new RuntimeException("Remplir non trouvé : " + m.getId());
                    }
                    dozerMapperBean.map(m, re);
                } else {
                    re = dozerMapperBean.map(m, RemplirEntity.class);
                }
                re.setRolesEntity(roleEntity);
                if (m.getMission() != null && m.getMission().getId() != null) {
                    MissionsEntity me = daoAccessorService.getRepository(MissionsRepository.class)
                            .findById(m.getMission().getId())
                            .orElseThrow(() -> new RuntimeException("Mission non trouvée : " + m.getMission().getId()));
                    re.setMissionsEntity(me);
                }
                RemplirEntity savedRe = daoAccessorService.getRepository(RemplirRepository.class).save(re);
                if (!roleEntity.getRemplirEntities().contains(savedRe)) {
                    roleEntity.getRemplirEntities().add(savedRe);
                }
            }
        }
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
