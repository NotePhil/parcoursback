package cmr.notep.impl;

import cmr.notep.api.IPersonnesApi;
import cmr.notep.business.PersonnesBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnes;
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
    public Personnes posterPersonne(Personnes Personnes) {
        return personnesBusiness.posterPersonne(Personnes);
    }

}
