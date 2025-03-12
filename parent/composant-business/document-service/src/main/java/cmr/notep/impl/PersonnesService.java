package cmr.notep.impl;

import cmr.notep.api.IPersonnesApi;
import cmr.notep.business.PersonnesBusiness;
import cmr.notep.modele.MacroPersonnes;
import cmr.notep.modele.Personnes;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class PersonnesService implements IPersonnesApi {

    private final PersonnesBusiness personnesBusiness;

    public PersonnesService(PersonnesBusiness personnesBusiness) {
        this.personnesBusiness = personnesBusiness;
    }

    @Override
    public Personnes avoirPersonne(@NonNull  String idPersonnes) {
        return personnesBusiness.avoirPersonne(idPersonnes);
    }

    @Override
    public List<MacroPersonnes> avoirParElemnt(@NonNull String value) {
        return personnesBusiness.avoirParElemnt(value);
    }

    @Override
    public List<Personnes> avoirToutPersonnes() {
        return personnesBusiness.avoirToutPersonnes();
    }

    @Override
    public void supprimerPersonne(Personnes Personnes) {
        personnesBusiness.supprimerPersonne(Personnes);
    }

    @Override
    public Personnes posterPersonne(Personnes Personnes) {
        return personnesBusiness.posterPersonne(Personnes);
    }
}
