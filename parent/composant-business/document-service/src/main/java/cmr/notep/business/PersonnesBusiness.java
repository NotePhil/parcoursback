package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.PersonnesEntity;
import cmr.notep.modele.*;
import cmr.notep.repository.PersonnesRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PersonnesBusiness {
    private final DaoAccessorService daoAccessorService;
    private final DistributeursBusiness distributeursBusiness ;
    private final PersonnesPhysiqueBusiness personnesPhysiqueBusiness ;
    private final PersonnesMoraleBusiness personnesMoraleBusiness;

    public PersonnesBusiness(DaoAccessorService daoAccessorService, DistributeursBusiness distributeursBusiness, PersonnesPhysiqueBusiness personnesPhysiqueBusiness, PersonnesMoraleBusiness personnesMoraleBusiness)
    {
        this.daoAccessorService = daoAccessorService;
        this.distributeursBusiness = distributeursBusiness;
        this.personnesPhysiqueBusiness = personnesPhysiqueBusiness;
        this.personnesMoraleBusiness = personnesMoraleBusiness;
    }

    public Personnes avoirPersonne(String id) {
        System.out.println("Recherche de personnes");
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PersonnesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("Personne inexistante")), Personnes.class);
    }

    public List<MacroPersonnes> avoirParElemnt(String value)
    {
        List<MacroPersonnes> outputItem = new ArrayList<>();

        List<Distributeurs> distributeurs = this.daoAccessorService.getRepository(PersonnesRepository.class).findByRaisonSocialeOnDistributeurs(value)
                .stream().map(distibuteur ->dozerMapperBean.map(distibuteur, Distributeurs.class))
                .toList();

        List<PersonnesMorale> personnesmorales = this.daoAccessorService.getRepository(PersonnesRepository.class).findByRaisonSocialeOnPersonneMorale(value)
                .stream().map(personnesmorale ->dozerMapperBean.map(personnesmorale, PersonnesMorale.class))
                .toList();

        List<PersonnesPhysique> personnesphysiques = this.daoAccessorService.getRepository(PersonnesRepository.class).findByNomOrByPrenomOnPersonnePhysique(value)
                .stream().map(personnesphysique ->dozerMapperBean.map(personnesphysique, PersonnesPhysique.class))
                .toList();

        for (Distributeurs distributeur : distributeurs) {
            outputItem.add(new MacroPersonnes("Distributeurs", distributeur));
        }

        for (PersonnesMorale personneMorale : personnesmorales) {
            outputItem.add(new MacroPersonnes("Personnes morale", personneMorale));
        }

        for (PersonnesPhysique personnePhysique : personnesphysiques) {
            outputItem.add(new MacroPersonnes("Personnes physique", personnePhysique));
        }

        if (outputItem.isEmpty())
            throw new IllegalStateException("Entrée "+value+" non trouvé!");

        else return outputItem;
    }

    public List<Personnes> avoirToutPersonnes() {

        List<PersonnesEntity> list_personnes_entity = daoAccessorService.getRepository(PersonnesRepository.class).findAll() ;

        List<Personnes> list_persones = list_personnes_entity.stream().map(cat ->dozerMapperBean.map(cat, Personnes.class))
                .collect(Collectors.toList());

        return list_persones;
    }

    public void supprimerPersonne(@NotNull Personnes Personnes)
    {
//        daoAccessorService.getRepository(PersonnesRepository.class)
//                .deleteById(String.valueOf(Personnes.getId()));
    }

    public Personnes posterPersonne(Personnes Personnes) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(PersonnesRepository.class)
                .save(dozerMapperBean.map(Personnes, PersonnesEntity.class)), Personnes.class);
    }
}
