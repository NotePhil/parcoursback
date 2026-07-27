package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.dao.FonctionnaliteEntity;
import cmr.notep.login.modele.Element;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.modele.ReorderRequest;
import cmr.notep.login.repository.FonctionnaliteRepository;
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
public class FonctionnaliteBusiness {

    private final FonctionnaliteRepository fonctionnaliteRepository;
    private final ElementBusiness elementBusiness;
    private final TraductionService traductionService;

    public FonctionnaliteBusiness(FonctionnaliteRepository fonctionnaliteRepository,
                                   ElementBusiness elementBusiness,
                                   TraductionService traductionService) {
        this.fonctionnaliteRepository = fonctionnaliteRepository;
        this.elementBusiness = elementBusiness;
        this.traductionService = traductionService;
    }

    public Fonctionnalite posterFonctionnalite(Fonctionnalite fonctionnalite) throws ParcoursException {
        FonctionnaliteEntity entity = dozerMapperBean.map(fonctionnalite, FonctionnaliteEntity.class);
        if (entity.getOrdre() == null || entity.getOrdre() == 0) {
            int next = fonctionnaliteRepository.countByGroupeId(StringUtils.defaultString(fonctionnalite.getId())) + 1;
            entity.setOrdre(next);
        }
        return dozerMapperBean.map(fonctionnaliteRepository.save(entity), Fonctionnalite.class);
    }

    public Fonctionnalite avoirFonctionnalite(String id) throws ParcoursException {
        FonctionnaliteEntity entity = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Fonctionnalite introuvable : " + id));
        Fonctionnalite f = dozerMapperBean.map(entity, Fonctionnalite.class);
        f.setElements(elementBusiness.avoirElementsDeFonctionnalite(id));
        return f;
    }

    public List<Fonctionnalite> avoirToutesFonctionnalites() {
        return fonctionnaliteRepository.findAll().stream().map(e -> {
            Fonctionnalite f = dozerMapperBean.map(e, Fonctionnalite.class);
            f.setElements(elementBusiness.avoirElementsDeFonctionnalite(e.getId()));
            return f;
        }).toList();
    }

    public Fonctionnalite modifierFonctionnalite(String id, Fonctionnalite fonctionnalite) throws ParcoursException {
        FonctionnaliteEntity entity = fonctionnaliteRepository.findById(id)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Fonctionnalite introuvable : " + id));
        entity.setFonction(fonctionnalite.getFonction());
        entity.setIcone(fonctionnalite.getIcone());
        entity.setActif(fonctionnalite.getActif());
        if (fonctionnalite.getOrdre() != null) entity.setOrdre(fonctionnalite.getOrdre());
        Fonctionnalite updated = dozerMapperBean.map(fonctionnaliteRepository.save(entity), Fonctionnalite.class);
        updated.setElements(elementBusiness.avoirElementsDeFonctionnalite(id));
        return updated;
    }

    public void supprimerFonctionnalite(String id) throws ParcoursException {
        if (!fonctionnaliteRepository.existsById(id))
            throw new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Fonctionnalite introuvable : " + id);
        fonctionnaliteRepository.deleteById(id);
    }

    public List<Fonctionnalite> avoirFonctionnalitesDuGroupe(String groupeId) {
        return fonctionnaliteRepository.findByGroupeIdOrderByOrdreAsc(groupeId).stream().map(e -> {
            Fonctionnalite f = dozerMapperBean.map(e, Fonctionnalite.class);
            f.setElements(elementBusiness.avoirElementsDeFonctionnalite(e.getId()));
            return f;
        }).toList();
    }

    /**
     * Réordonne les fonctionnalités selon la liste d'ids fournie.
     * Renumérotation atomique : ordre 1, 2, 3… sans trous.
     */
    public List<Fonctionnalite> reordonner(ReorderRequest request) throws ParcoursException {
        if (request == null || request.getIds() == null)
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_INPUT, "Liste d'ids obligatoire");
        List<Fonctionnalite> result = new ArrayList<>();
        for (int i = 0; i < request.getIds().size(); i++) {
            fonctionnaliteRepository.updateOrdre(request.getIds().get(i), i + 1);
            result.add(avoirFonctionnalite(request.getIds().get(i)));
        }
        return result;
    }

    public Fonctionnalite traduire(Fonctionnalite fonctionnalite, String langue) throws ParcoursException {
        if (fonctionnalite == null || StringUtils.isBlank(langue) || "fr".equalsIgnoreCase(langue)) return fonctionnalite;
        List<Element> elementsTraduites = fonctionnalite.getElements() == null ? null :
                fonctionnalite.getElements().stream().map(el -> {
                    try { return elementBusiness.traduire(el, langue); }
                    catch (ParcoursException e) { return el; }
                }).toList();
        return Fonctionnalite.builder()
                .id(fonctionnalite.getId()).fonction(traductionService.traduire(fonctionnalite.getFonction(), langue))
                .icone(fonctionnalite.getIcone()).actif(fonctionnalite.getActif()).ordre(fonctionnalite.getOrdre())
                .elements(elementsTraduites)
                .build();
    }
}
