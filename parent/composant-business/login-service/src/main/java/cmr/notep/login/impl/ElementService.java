package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.IElementApi;
import cmr.notep.login.business.ElementBusiness;
import cmr.notep.login.modele.Element;
import cmr.notep.login.modele.ReorderRequest;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ElementService implements IElementApi {

    private final ElementBusiness elementBusiness;

    public ElementService(ElementBusiness elementBusiness) {
        this.elementBusiness = elementBusiness;
    }

    @Override public Element posterElement(Element element) throws ParcoursException { return elementBusiness.posterElement(element); }
    @Override public Element avoirElement(String id) throws ParcoursException { return elementBusiness.avoirElement(id); }
    @Override public List<Element> avoirTousElements() throws ParcoursException { return elementBusiness.avoirTousElements(); }
    @Override public Element modifierElement(String id, Element element) throws ParcoursException { return elementBusiness.modifierElement(id, element); }
    @Override public void supprimerElement(String id) throws ParcoursException { elementBusiness.supprimerElement(id); }
    @Override public List<Element> reordonnerElements(ReorderRequest request) throws ParcoursException { return elementBusiness.reordonner(request); }
}
