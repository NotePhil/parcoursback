package cmr.notep.impl;

import cmr.notep.api.IActionsApi;
import cmr.notep.business.ActionsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Actions;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class ActionsService implements IActionsApi {

    private final ActionsBusiness actionBusiness ;

    public ActionsService(ActionsBusiness actionBusiness) {
        this.actionBusiness = actionBusiness;
    }

    @Override
    public Actions avoirAction(String idaction) throws ParcoursException {
        return null;
    }

    @Override
    public List<Actions> avoirToutesActions() {
        return actionBusiness.avoirToutesActions();
    }

    @Override
    public void supprimerActions(Actions actions) {

    }

    @Override
    public Actions posterAction(Actions actions) {
        return null;
    }
}
