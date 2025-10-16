package cmr.notep.impl;

import cmr.notep.api.IPersonnesApi;
import cmr.notep.business.PersonnesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.IPersonnes;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PersonnesPhysique;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class PersonnesService implements IPersonnesApi {

    private final PersonnesBusiness personnesBusiness;

    public PersonnesService(PersonnesBusiness personnesBusiness) {
        this.personnesBusiness = personnesBusiness;
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
    public List<Personnes> avoirToutPersonnes() {
        return personnesBusiness.avoirToutPersonnes();
    }

    @Override
    public List<IPersonnes> avoirToutPersonnesPhysiques() {
        return  personnesBusiness.avoirToutPersonnesPhysiques();
    }

    @Override
    public PersonnesPhysique posterPersonnePhysique(PersonnesPhysique personnesPhysique) {
        return personnesBusiness.posterPersonnePhysique(personnesPhysique);
    }

    //@Override
    public void supprimerPersonne(Personnes Personnes) {
        personnesBusiness.supprimerPersonne(Personnes);
    }

    //@Override
    public Personnes posterPersonne(Personnes Personnes) {
        return personnesBusiness.posterPersonne(Personnes);
    }
}
