package cmr.notep.impl;

import cmr.notep.api.IActionsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Actions;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class ActionsService implements IActionsApi {
    @Override
    public Actions avoirAction(String idaction) throws ParcoursException {
        return null;
    }

    @Override
    public List<Actions> avoirToutesActions() {
        System.out.println("teste des actions");
        return List.of();
    }

    @Override
    public void supprimerActions(Actions actions) {

    }

    @Override
    public Actions posterAction(Actions actions) {
        return null;
    }
}
