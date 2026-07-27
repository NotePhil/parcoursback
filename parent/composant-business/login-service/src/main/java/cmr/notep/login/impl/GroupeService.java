package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IGroupeApi;
import cmr.notep.login.business.GroupeBusiness;
import cmr.notep.login.modele.Groupe;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class GroupeService implements IGroupeApi {

    private final GroupeBusiness groupeBusiness;

    public GroupeService(GroupeBusiness groupeBusiness) {
        this.groupeBusiness = groupeBusiness;
    }

    @Override public Groupe posterGroupe(Groupe groupe) throws ParcoursException { return groupeBusiness.posterGroupe(groupe); }
    @Override public Groupe avoirGroupe(String id) throws ParcoursException { return groupeBusiness.avoirGroupe(id); }
    @Override public List<Groupe> avoirTousGroupes() throws ParcoursException { return groupeBusiness.avoirTousGroupes(); }
    @Override public Groupe modifierGroupe(String id, Groupe groupe) throws ParcoursException { return groupeBusiness.modifierGroupe(id, groupe); }
    @Override public void supprimerGroupe(String id) throws ParcoursException { groupeBusiness.supprimerGroupe(id); }
}
