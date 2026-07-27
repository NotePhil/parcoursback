package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.JouerRolesEntity;
import cmr.notep.dao.PersonnelsEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.JouerRoles;
import cmr.notep.modele.Personnels;
import cmr.notep.repository.JouerRolesRepository;
import cmr.notep.repository.PersonnelsRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
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
            dozerMapperBean.map(personnel, entitySaved);
        } else {
            entitySaved = dozerMapperBean.map(personnel, PersonnelsEntity.class);
        }

        gererJouerRoles(personnel, entitySaved);

        entitySaved = personnelsRepo.save(entitySaved);
        return dozerMapperBean.map(entitySaved, Personnels.class);
    }

    private void gererJouerRoles(Personnels personnel, PersonnelsEntity entitySaved) throws ParcoursException {
        List<JouerRoles> rolesList = personnel.getRoles();
        JouerRolesRepository jouerRolesRepo = this.daoAccessorService.getRepository(JouerRolesRepository.class);

        List<JouerRolesEntity> existingRoles = entitySaved.getJouerRolesEntities() != null
                ? new ArrayList<>(entitySaved.getJouerRolesEntities())
                : new ArrayList<>();

        // Si liste absente ou vide => purger tous les rôles
        if (CollectionUtils.isEmpty(rolesList)) {
            try {
                for (JouerRolesEntity existing : existingRoles) {
                    jouerRolesRepo.delete(existing);
                }
                if (entitySaved.getJouerRolesEntities() != null) {
                    entitySaved.getJouerRolesEntities().clear();
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les anciens rôles du personnel: " + e.getMessage(), e);
            }
            return;
        }

        List<String> newIds = rolesList.stream()
                .filter(r -> r.getId() != null)
                .map(JouerRoles::getId)
                .collect(Collectors.toList());

        // Supprimer les rôles non présents dans la nouvelle liste
        for (JouerRolesEntity existing : existingRoles) {
            if (!newIds.contains(existing.getId())) {
                try {
                    jouerRolesRepo.delete(existing);
                    entitySaved.getJouerRolesEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer le rôle " + existing.getId() + ": " + e.getMessage(), e);
                }
            }
        }

        if (entitySaved.getJouerRolesEntities() == null) {
            entitySaved.setJouerRolesEntities(new ArrayList<>());
        }

        // Ajouter ou mettre à jour les rôles
        for (JouerRoles role : rolesList) {
            JouerRolesEntity jouerRolesEntity;
            if (role.getId() != null) {
                jouerRolesEntity = jouerRolesRepo.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("JouerRole non trouvé : " + role.getId()));
                dozerMapperBean.map(role, jouerRolesEntity);
            } else {
                jouerRolesEntity = dozerMapperBean.map(role, JouerRolesEntity.class);
                jouerRolesEntity.setId(null);
            }
            jouerRolesEntity.setPersonnelsEntity(entitySaved);
            try {
                JouerRolesEntity savedRole = jouerRolesRepo.save(jouerRolesEntity);
                if (!entitySaved.getJouerRolesEntities().contains(savedRole)) {
                    entitySaved.getJouerRolesEntities().add(savedRole);
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'enregistrer le rôle du personnel: " + e.getMessage(), e);
            }
        }
    }

}
