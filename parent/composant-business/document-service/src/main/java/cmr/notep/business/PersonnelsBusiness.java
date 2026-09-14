package cmr.notep.business;

import cmr.notep.config.mapper.MapperBean;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.JouerRolesEntity;
import cmr.notep.dao.PersonnelsEntity;
import cmr.notep.dao.RolesEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.JouerRoles;
import cmr.notep.modele.Personnels;
import cmr.notep.repository.JouerRolesRepository;
import cmr.notep.repository.PersonnelsRepository;
import cmr.notep.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class PersonnelsBusiness  {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public PersonnelsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Personnels avoirPersonnel(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PersonnelsRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("personnel inexistante")), Personnels.class);
    }

    public List<Personnels> avoirToutPersonnels() {
        return daoAccessorService.getRepository(PersonnelsRepository.class).findAll()
                .stream().map(personnel ->dozerMapperBean.map(personnel, Personnels.class))
                .collect(Collectors.toList());
    }

    public void supprimerPersonnel(Personnels Personnels)
    {
        daoAccessorService.getRepository(PersonnelsRepository.class)
                .deleteById(Personnels.getId().toString());
    }

    public Personnels posterPersonnel(Personnels personnel) throws ParcoursException {
        PersonnelsRepository personnelsRepo = this.daoAccessorService.getRepository(PersonnelsRepository.class);
        PersonnelsEntity entitySaved;

        if (personnel.getId() != null) {
            entitySaved = personnelsRepo.findById(personnel.getId())
                    .orElseThrow(() -> new RuntimeException("Personnel non trouvé : " + personnel.getId()));
            MapperBean.getDozerMapperWithoutList().map(personnel, entitySaved);
        } else {
            entitySaved = dozerMapperBean.map(personnel, PersonnelsEntity.class);
        }

        gererJouerRoles(personnel, entitySaved);

        entitySaved = personnelsRepo.save(entitySaved);
        Personnels savedPersonnel = dozerMapperBean.map(entitySaved, Personnels.class);
        if (!CollectionUtils.isEmpty(savedPersonnel.getRoles())) {
            // L'identifiant exposé des rôles suit la clé fonctionnelle role.id.
            savedPersonnel.getRoles().forEach(role -> {
                if (role.getRole() != null && role.getRole().getId() != null) {
                    role.setId(role.getRole().getId());
                }
            });
        }
        return savedPersonnel;
    }

    private void gererJouerRoles(Personnels personnel, PersonnelsEntity entitySaved) throws ParcoursException {
        List<JouerRoles> rolesList = personnel.getRoles();
        JouerRolesRepository jouerRolesRepo = this.daoAccessorService.getRepository(JouerRolesRepository.class);
        RolesRepository rolesRepository = this.daoAccessorService.getRepository(RolesRepository.class);

        List<JouerRolesEntity> existingRoles = entitySaved.getId() != null
                ? new ArrayList<>(jouerRolesRepo.findByPersonnelsEntityId(entitySaved.getId()))
                : (entitySaved.getRoles() != null ? new ArrayList<>(entitySaved.getRoles()) : new ArrayList<>());

        // Si liste absente ou vide => purger tous les rôles
        if (CollectionUtils.isEmpty(rolesList)) {
            try {
                for (JouerRolesEntity existing : existingRoles) {
                    jouerRolesRepo.delete(existing);
                }
                if (entitySaved.getRoles() != null) {
                    entitySaved.getRoles().clear();
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les anciens rôles du personnel: " + e.getMessage(), e);
            }
            return;
        }

        Map<String, JouerRoles> incomingByRoleId = new LinkedHashMap<>();
        for (JouerRoles role : rolesList) {
            if (role == null || role.getRole() == null || role.getRole().getId() == null) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Chaque élément de roles doit contenir role.id");
            }

            String roleId = role.getRole().getId();
            if (incomingByRoleId.putIfAbsent(roleId, role) != null) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "role.id dupliqué dans la requête: " + roleId);
            }
        }

        Map<String, JouerRolesEntity> existingByRoleId = new LinkedHashMap<>();
        for (JouerRolesEntity existing : existingRoles) {
            if (existing.getRolesEntity() != null && existing.getRolesEntity().getId() != null) {
                existingByRoleId.put(existing.getRolesEntity().getId(), existing);
            }
        }

        // Supprimer les associations non présentes dans la nouvelle liste (clé métier: role.id)
        for (JouerRolesEntity existing : existingRoles) {
            String existingRoleId = existing.getRolesEntity() != null ? existing.getRolesEntity().getId() : null;
            if (existingRoleId == null || !incomingByRoleId.containsKey(existingRoleId)) {
                try {
                    jouerRolesRepo.delete(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer le rôle " + existing.getId() + ": " + e.getMessage(), e);
                }
            }
        }

        if (entitySaved.getRoles() == null) {
            entitySaved.setRoles(new ArrayList<>());
        } else {
            entitySaved.getRoles().clear();
        }

        // Ajouter ou mettre à jour les associations restantes
        for (Map.Entry<String, JouerRoles> entry : incomingByRoleId.entrySet()) {
            String roleId = entry.getKey();
            JouerRoles role = entry.getValue();
            RolesEntity roleEntity = rolesRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role non trouvé : " + roleId));
            JouerRolesEntity jouerRolesEntity;

            if (role.getId() != null) {
                jouerRolesEntity = jouerRolesRepo.findById(role.getId())
                        .orElse(existingByRoleId.get(roleId));
                if (jouerRolesEntity == null) {
                    throw new RuntimeException("JouerRole non trouvé : " + role.getId());
                }

                String mappedRoleId = jouerRolesEntity.getRolesEntity() != null ? jouerRolesEntity.getRolesEntity().getId() : null;
                if (mappedRoleId != null && !Objects.equals(mappedRoleId, roleId)) {
                    throw new RuntimeException("Incohérence entre jouerRole.id et role.id : " + role.getId());
                }
            } else {
                jouerRolesEntity = existingByRoleId.get(roleId);
                if (jouerRolesEntity == null) {
                    jouerRolesEntity = new JouerRolesEntity();
                }
            }

            // Mapping explicite pour éviter les effets de bord Dozer sur les associations/id.
            jouerRolesEntity.setEtat(role.getEtat());
            jouerRolesEntity.setDateDebut(role.getDateDebut());
            jouerRolesEntity.setDateFin(role.getDateFin());
            jouerRolesEntity.setRolesEntity(roleEntity);
            jouerRolesEntity.setPersonnelsEntity(entitySaved);
            try {
                JouerRolesEntity savedRole = jouerRolesRepo.save(jouerRolesEntity);
                entitySaved.getRoles().add(savedRole);
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'enregistrer le rôle du personnel: " + e.getMessage(), e);
            }
        }
    }

}
