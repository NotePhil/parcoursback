package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.*;
import cmr.notep.repository.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PersonnesBusiness {
    private final DaoAccessorService daoAccessorService;

    public PersonnesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Personnes> avoirToutePersonnePhyMor(){
        List<Pair<Class<?>, Class<?>>> repoClasses = List.of(
                Pair.of(PersonnePhysiqueRepository.class, PersonnesPhysique.class),
                Pair.of(PersonneMoraleRepository.class, PersonnesMorale.class)
        );

        List<Personnes> personnesList = new ArrayList<>();

        for (Pair<Class<?>, Class<?>> repoAndClass : repoClasses){

            JpaRepository<?, String> repo = (JpaRepository<?, String>) this.daoAccessorService.getRepository(repoAndClass.getLeft());

            List<?> entities = repo.findAll().stream().map(entity -> dozerMapperBean.map(entity , repoAndClass.getRight()))
                    .collect(Collectors.toUnmodifiableList());

            personnesList.addAll((Collection<? extends Personnes>) entities);

        }

        return personnesList ;
    }

    public Personnes avoirPersonne(@NonNull String id) throws ParcoursException {

        List<Pair<Class<?>, Class<?>>> repositories = List.of(
                Pair.of(PersonnePhysiqueRepository.class, PersonnesPhysique.class),
                Pair.of(PersonneMoraleRepository.class, PersonnesMorale.class),
                Pair.of(DistributeursRepository.class, Distributeurs.class),
                Pair.of(PersonnelsRepository.class, Personnels.class)
        );

        for (Pair<Class<?>, Class<?>> repoAndClass : repositories) {
            JpaRepository<?, String> repo = (JpaRepository<?, String>) this.daoAccessorService.getRepository(repoAndClass.getLeft());
            Optional<?> entityOpt = repo.findById(id);
            if (entityOpt.isPresent()) {
                return (Personnes) dozerMapperBean.map(entityOpt.get(), repoAndClass.getRight());
            }
        }

        throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Personne inexistante");

    }

    public List<Personnes> avoirToutPersonnes() {

        List<Pair<Class<?>, Class<?>>> repoClasses = List.of(
                Pair.of(PersonnePhysiqueRepository.class, PersonnesPhysique.class),
                Pair.of(PersonneMoraleRepository.class, PersonnesMorale.class),
                Pair.of(DistributeursRepository.class, Distributeurs.class),
                Pair.of(PersonnelsRepository.class, Personnels.class)
        );

        List<Personnes> personnesList = new ArrayList<>();

        for (Pair<Class<?>, Class<?>> repoAndClass : repoClasses){

            JpaRepository<?, String> repo = (JpaRepository<?, String>) this.daoAccessorService.getRepository(repoAndClass.getLeft());

            List<?> entities = repo.findAll().stream().map(entity -> dozerMapperBean.map(entity , repoAndClass.getRight()))
                    .collect(Collectors.toUnmodifiableList());

            personnesList.addAll((Collection<? extends Personnes>) entities);

        }

        return personnesList ;
    }

    public void supprimerPersonne(Personnes personne)
    {
        daoAccessorService.getRepository(PersonnesRepository.class)
                .deleteById(String.valueOf(personne.getId()));
    }

    public Personnes posterPersonne(Personnes personne) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PersonnesRepository.class)
                        .save(dozerMapperBean.map(personne, PersonnesEntity.class)),
                Personnes.class
        );
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
