package cmr.notep.impl;

import cmr.notep.api.IPersonnesApi;
import cmr.notep.business.PersonnesBusiness;
import cmr.notep.business.PersonnesMoraleBusiness;
import cmr.notep.business.PersonnesPhysiqueBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PersonnesPhysique;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Transactional
@Primary
public class PersonnesService implements IPersonnesApi {

    private final PersonnesBusiness personnesBusiness;
    private final PersonnesPhysiqueBusiness personnesPhysiqueBusiness;
    private final PersonnesMoraleBusiness personnesMoraleBusiness;

    public PersonnesService(PersonnesBusiness personnesBusiness, PersonnesPhysiqueBusiness personnesPhysiqueBusiness, PersonnesMoraleBusiness personnesMoraleBusiness) {
        this.personnesBusiness = personnesBusiness;
        this.personnesPhysiqueBusiness = personnesPhysiqueBusiness;
        this.personnesMoraleBusiness = personnesMoraleBusiness;
    }

    @Override
    public Personnes avoirPersonne(@NonNull String idPersonnes) {
        try {
            return personnesBusiness.avoirPersonne(idPersonnes);
        } catch (ParcoursException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PersonnesPhysique> avoirToutPatient()
    {
        return this.personnesPhysiqueBusiness.avoirToutPersonnesPhysique();
    }

    @Override
    public PersonnesPhysique avoirPatient (@NonNull String idPersonnes) {
        return this.personnesPhysiqueBusiness.avoirPersonnePhysique(idPersonnes);
    }

    @Override
    public Personnes posterPatient(PersonnesPhysique Personnes) {
        return this.personnesPhysiqueBusiness.posterPatient(Personnes);
    }

    @Override
    public List<Personnes> avoirToutPersonnes() {
        return personnesBusiness.avoirToutPersonnes();
    }

    @Override
    public Personnes posterPersonne(Personnes Personnes) {
        return personnesBusiness.posterPersonne(Personnes);
    }

}
