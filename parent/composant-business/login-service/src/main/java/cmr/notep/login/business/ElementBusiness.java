package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.ElementEntity;
import cmr.notep.login.modele.Action;
import cmr.notep.login.modele.Element;
import cmr.notep.login.modele.ReorderRequest;
import cmr.notep.login.repository.ActionRepository;
import cmr.notep.login.repository.ElementRepository;
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
public class ElementBusiness {

    private final ElementRepository elementRepository;
    private final ActionRepository actionRepository;
    private final ActionBusiness actionBusiness;
    private final TraductionService traductionService;

    public ElementBusiness(ElementRepository elementRepository, ActionRepository actionRepository,
                           ActionBusiness actionBusiness, TraductionService traductionService) {
        this.elementRepository = elementRepository;
        this.actionRepository = actionRepository;
        this.actionBusiness = actionBusiness;
        this.traductionService = traductionService;
    }

    public Element posterElement(Element element) throws ParcoursException {
        ElementEntity entity = dozerMapperBean.map(element, ElementEntity.class);
        if (entity.getOrdre() == null || entity.getOrdre() == 0) {
            int next = elementRepository.countByFonctionnaliteId(StringUtils.defaultString(element.getId())) + 1;
            entity.setOrdre(next);
        }
        ElementEntity saved = elementRepository.save(entity);
        return dozerMapperBean.map(saved, Element.class);
    }

    public Element avoirElement(String id) throws ParcoursException {
        ElementEntity entity = elementRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Element introuvable : " + id));
        Element element = dozerMapperBean.map(entity, Element.class);
        element.setAction(actionBusiness.avoirActionsDeElement(id));
        return element;
    }

    public List<Element> avoirTousElements() {
        return elementRepository.findAll().stream().map(e -> {
            Element el = dozerMapperBean.map(e, Element.class);
            el.setAction(actionBusiness.avoirActionsDeElement(e.getId()));
            return el;
        }).toList();
    }

    public List<Element> avoirElementsDeFonctionnalite(String fonctionnaliteId) {
        return elementRepository.findByFonctionnaliteIdOrderByOrdreAsc(fonctionnaliteId).stream().map(e -> {
            Element el = dozerMapperBean.map(e, Element.class);
            el.setAction(actionBusiness.avoirActionsDeElement(e.getId()));
            return el;
        }).toList();
    }

    public Element modifierElement(String id, Element element) throws ParcoursException {
        ElementEntity entity = elementRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Element introuvable : " + id));
        entity.setNom(element.getNom());
        entity.setLien(element.getLien());
        entity.setBouton(element.getBouton());
        if (element.getOrdre() != null) entity.setOrdre(element.getOrdre());
        Element updated = dozerMapperBean.map(elementRepository.save(entity), Element.class);
        updated.setAction(actionBusiness.avoirActionsDeElement(id));
        return updated;
    }

    public void supprimerElement(String id) throws ParcoursException {
        if (!elementRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Element introuvable : " + id);
        elementRepository.deleteById(id);
    }

    /**
     * Réordonne les éléments selon la liste d'ids fournie.
     * Renumérotation atomique : ordre 1, 2, 3… sans trous.
     */
    public List<Element> reordonner(ReorderRequest request) throws ParcoursException {
        if (request == null || request.getIds() == null)
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_INPUT, "Liste d'ids obligatoire");
        List<Element> result = new ArrayList<>();
        for (int i = 0; i < request.getIds().size(); i++) {
            elementRepository.updateOrdre(request.getIds().get(i), i + 1);
            result.add(avoirElement(request.getIds().get(i)));
        }
        return result;
    }

    public Element traduire(Element element, String langue) throws ParcoursException {
        if (element == null || StringUtils.isBlank(langue) || "fr".equalsIgnoreCase(langue)) return element;
        List<Action> actionsTraduites = element.getAction() == null ? null :
                element.getAction().stream().map(a -> {
                    try { return actionBusiness.traduire(a, langue); }
                    catch (ParcoursException e) { return a; }
                }).toList();
        return Element.builder()
                .id(element.getId()).nom(traductionService.traduire(element.getNom(), langue))
                .lien(element.getLien()).bouton(element.getBouton()).ordre(element.getOrdre())
                .action(actionsTraduites)
                .build();
    }
}
