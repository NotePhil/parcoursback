package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.ActionEntity;
import cmr.notep.login.modele.Action;
import cmr.notep.login.modele.ReorderRequest;
import cmr.notep.login.repository.ActionRepository;
import cmr.notep.login.traduction.TraductionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ActionBusiness {

    private final ActionRepository actionRepository;
    private final TraductionService traductionService;

    public ActionBusiness(ActionRepository actionRepository, TraductionService traductionService) {
        this.actionRepository = actionRepository;
        this.traductionService = traductionService;
    }

    public Action posterAction(Action action) throws ParcoursException {
        ActionEntity entity = dozerMapperBean.map(action, ActionEntity.class);
        if (entity.getOrdre() == null || entity.getOrdre() == 0) {
            int next = actionRepository.countByElementId(StringUtils.defaultString(action.getId())) + 1;
            entity.setOrdre(next);
        }
        return dozerMapperBean.map(actionRepository.save(entity), Action.class);
    }

    public Action avoirAction(String id) throws ParcoursException {
        return actionRepository.findById(id)
                .map(e -> dozerMapperBean.map(e, Action.class))
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Action introuvable : " + id));
    }

    public List<Action> avoirToutesActions() {
        return actionRepository.findAll().stream()
                .map(e -> dozerMapperBean.map(e, Action.class))
                .toList();
    }

    public List<Action> avoirActionsDeElement(String elementId) {
        return actionRepository.findByElementIdOrderByOrdreAsc(elementId).stream()
                .map(e -> dozerMapperBean.map(e, Action.class))
                .toList();
    }

    public Action modifierAction(String id, Action action) throws ParcoursException {
        ActionEntity entity = actionRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Action introuvable : " + id));
        entity.setNom(action.getNom());
        entity.setLien(action.getLien());
        entity.setBouton(action.getBouton());
        entity.setType(action.getType());
        if (action.getOrdre() != null) entity.setOrdre(action.getOrdre());
        return dozerMapperBean.map(actionRepository.save(entity), Action.class);
    }

    public void supprimerAction(String id) throws ParcoursException {
        if (!actionRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Action introuvable : " + id);
        actionRepository.deleteById(id);
    }

    /**
     * Réordonne les actions selon la liste d'ids fournie.
     * Renumérotation atomique : ordre 1, 2, 3… sans trous.
     */
    public List<Action> reordonner(ReorderRequest request) throws ParcoursException {
        if (request == null || request.getIds() == null)
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_INPUT, "Liste d'ids obligatoire");
        List<Action> result = new ArrayList<>();
        for (int i = 0; i < request.getIds().size(); i++) {
            actionRepository.updateOrdre(request.getIds().get(i), i + 1);
            result.add(avoirAction(request.getIds().get(i)));
        }
        return result;
    }

    public Action traduire(Action action, String langue) throws ParcoursException {
        if (action == null || StringUtils.isBlank(langue) || "fr".equalsIgnoreCase(langue)) return action;
        return Action.builder()
                .id(action.getId()).nom(traductionService.traduire(action.getNom(), langue))
                .lien(action.getLien()).bouton(action.getBouton()).type(action.getType()).ordre(action.getOrdre())
                .build();
    }
}
