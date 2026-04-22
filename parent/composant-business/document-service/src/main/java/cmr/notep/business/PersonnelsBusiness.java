package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.JouerRolesEntity;
import cmr.notep.dao.PersonnelsEntity;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class PersonnelsBusiness  {

    private final DaoAccessorService daoAccessorService;
    private final RolesBusiness rolesBusiness;
    private final DozerBeanMapper dozerMapperBean;

    public PersonnelsBusiness(DaoAccessorService daoAccessorService, RolesBusiness rolesBusiness, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.rolesBusiness = rolesBusiness;
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

    public Personnels posterPersonnel(Personnels personnel) {
        //faire les controles sur les différents attributs de l'objet personnel
        //sauvegarder le personnel
        PersonnelsRepository personnelsRepo = this.daoAccessorService.getRepository(PersonnelsRepository.class);
        JouerRolesRepository jouerRolesRepo = this.daoAccessorService.getRepository(JouerRolesRepository.class);

        PersonnelsEntity entitySaved = dozerMapperBean.map(personnel, PersonnelsEntity.class);

        entitySaved = personnelsRepo.save(entitySaved);

        // Supprimer les anciens rôles avant d'enregistrer les nouveaux
        jouerRolesRepo.deleteByPersonnelsEntityId(entitySaved.getId());
        jouerRolesRepo.flush();

        if (!CollectionUtils.isEmpty(personnel.getRoles())) {
            entitySaved.setJouerRolesEntities(new ArrayList<>());
            PersonnelsEntity finalEntitySaved = entitySaved;
            entitySaved.getJouerRolesEntities().addAll(
                    personnel.getRoles().stream()
                            .map(jouerRole -> {
                                JouerRolesEntity jouerRolesEntity = dozerMapperBean.map(jouerRole, JouerRolesEntity.class);
                                jouerRolesEntity.setId(null); // forcer la création d'un nouvel enregistrement
                                jouerRolesEntity.setPersonnelsEntity(finalEntitySaved);
                                jouerRolesEntity = enregistrerJouerRole(jouerRolesEntity);
                                return jouerRolesEntity;
                            })
                            .collect(Collectors.toList())
            );
            entitySaved = personnelsRepo.save(entitySaved);
        }

        return dozerMapperBean.map(entitySaved, Personnels.class);
    }

    private JouerRolesEntity enregistrerJouerRole(JouerRolesEntity jouerRole) {
        //TODO faire les controles sur les différents attributs de l'objet jouerRole
        if(jouerRole.getRolesEntity().getId() != null)
            rolesBusiness.avoirRole(jouerRole.getRolesEntity().getId());
        return this.daoAccessorService.getRepository(JouerRolesRepository.class)
                .save(jouerRole);
    }


}
