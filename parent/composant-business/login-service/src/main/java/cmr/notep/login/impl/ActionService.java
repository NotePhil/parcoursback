package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IActionApi;
import cmr.notep.login.business.ActionBusiness;
import cmr.notep.login.modele.Action;
import cmr.notep.login.modele.ReorderRequest;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ActionService implements IActionApi {

    private final ActionBusiness actionBusiness;

    public ActionService(ActionBusiness actionBusiness) {
        this.actionBusiness = actionBusiness;
    }

    @Override public Action posterAction(Action action) throws ParcoursException { return actionBusiness.posterAction(action); }
    @Override public Action avoirAction(String id) throws ParcoursException { return actionBusiness.avoirAction(id); }
    @Override public List<Action> avoirToutesActions() throws ParcoursException { return actionBusiness.avoirToutesActions(); }
    @Override public Action modifierAction(String id, Action action) throws ParcoursException { return actionBusiness.modifierAction(id, action); }
    @Override public void supprimerAction(String id) throws ParcoursException { actionBusiness.supprimerAction(id); }
    @Override public List<Action> reordonnerActions(ReorderRequest request) throws ParcoursException { return actionBusiness.reordonner(request); }
}
