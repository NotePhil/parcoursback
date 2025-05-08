package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.*;
import cmr.notep.repository.PersonnesRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PersonnesBusiness {
    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public PersonnesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Personnes avoirPersonne(@NonNull String id) throws ParcoursException {
        PersonnesEntity personnesEntity = this.daoAccessorService.getRepository(PersonnesRepository.class)
                .findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,"Personne ou Distributeur inexistant"));
        return mapPersonnesEntityToPersonnes(personnesEntity);

    }

    public List<IPersonnes> avoirToutPersonnes() {
        return daoAccessorService.getRepository(PersonnesRepository.class).findAll()
                .stream().map(personne ->mapPersonnesEntityToPersonnes(personne))
                .collect(Collectors.toList());
    }

    public void supprimerPersonne(Personnes personne)
    {
        daoAccessorService.getRepository(PersonnesRepository.class)
                .deleteById(String.valueOf(personne.getId()));
    }

    public Personnes posterPersonne(Personnes personne) {
        return mapPersonnesEntityToPersonnes( this.daoAccessorService.getRepository(PersonnesRepository.class)
                .save(mapPersonnesToPersonnesEntity(personne)));
    }

    private Personnes mapPersonnesEntityToPersonnes(PersonnesEntity personnesEntity) {
        if(personnesEntity instanceof DistributeursEntity)
            return dozerMapperBean.map(personnesEntity, Distributeurs.class);
        else if (personnesEntity instanceof PersonnesPhysiquesEntity)
            return dozerMapperBean.map(personnesEntity, PersonnesPhysique.class);
        else if (personnesEntity instanceof PersonnesMoralesEntity)
            return dozerMapperBean.map(personnesEntity, PersonnesMorale.class);
        else if (personnesEntity instanceof PersonnelsEntity)
            return dozerMapperBean.map(personnesEntity, Personnels.class);
        else
            return dozerMapperBean.map(personnesEntity, Personnes.class);
    }

    private PersonnesEntity mapPersonnesToPersonnesEntity(Personnes personnes) {
        if(personnes instanceof Distributeurs)
            return dozerMapperBean.map(personnes, DistributeursEntity.class);
        else if (personnes instanceof PersonnesPhysique)
            return dozerMapperBean.map(personnes, PersonnesPhysiquesEntity.class);
        else if (personnes instanceof PersonnesMorale)
            return dozerMapperBean.map(personnes, PersonnesMoralesEntity.class);
        else if (personnes instanceof Personnels)
            return dozerMapperBean.map(personnes, PersonnelsEntity.class);
        else
            return dozerMapperBean.map(personnes, PersonnesEntity.class);
    }
}
