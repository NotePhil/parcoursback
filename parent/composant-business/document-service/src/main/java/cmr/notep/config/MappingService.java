package cmr.notep.config;

import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PersonnesPhysique;
import cmr.notep.modele.PersonnesMorale;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public Distributeurs mapPersonnesToDistributeurs(Personnes personnes) {
        return DocumentConfig.dozerMapperBean.map(personnes, Distributeurs.class);
    }

    public PersonnesPhysique mapPersonnesToPersonnesPhysique(Personnes personnes) {
        return DocumentConfig.dozerMapperBean.map(personnes, PersonnesPhysique.class);
    }

    public PersonnesMorale mapPersonnesToPersonnesMorale(Personnes personnes) {
        return DocumentConfig.dozerMapperBean.map(personnes, PersonnesMorale.class);
    }
}