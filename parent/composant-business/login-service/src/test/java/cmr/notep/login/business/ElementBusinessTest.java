package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.ElementEntity;
import cmr.notep.login.modele.Action;
import cmr.notep.login.modele.Element;
import cmr.notep.login.repository.ActionRepository;
import cmr.notep.login.repository.ElementRepository;
import cmr.notep.login.traduction.TraductionService;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElementBusinessTest {

    @Mock ElementRepository elementRepository;
    @Mock ActionRepository actionRepository;
    @Mock ActionBusiness actionBusiness;
    @Mock TraductionService traductionService;
    @InjectMocks ElementBusiness elementBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    private ElementEntity elementEntity(String id, String nom) {
        ElementEntity e = new ElementEntity();
        e.setId(id); e.setNom(nom); e.setLien("/test"); e.setBouton("false"); e.setFonctionnaliteId("f1");
        return e;
    }

    @Test
    void avoirElement_devraitRetournerAvecActions() throws ParcoursException {
        ElementEntity entity = elementEntity("e1", "Créer");
        when(elementRepository.findById("e1")).thenReturn(Optional.of(entity));
        when(actionRepository.findByElementId("e1")).thenReturn(List.of());

        Element result = elementBusiness.avoirElement("e1");

        assertThat(result.getNom()).isEqualTo("Créer");
        assertThat(result.getAction()).isEmpty();
    }

    @Test
    void avoirElement_devraitLeverExceptionSiInexistant() {
        when(elementRepository.findById("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> elementBusiness.avoirElement("x"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void posterElement_devraitPersister() throws ParcoursException {
        ElementEntity saved = elementEntity("e1", "Rechercher");
        when(elementRepository.save(any())).thenReturn(saved);

        Element result = elementBusiness.posterElement(Element.builder().nom("Rechercher").lien("/list").build());

        assertThat(result.getNom()).isEqualTo("Rechercher");
    }

    @Test
    void avoirTousElements_devraitRetournerListe() {
        when(elementRepository.findAll()).thenReturn(
                List.of(elementEntity("e1", "Créer"), elementEntity("e2", "Rechercher")));
        when(actionRepository.findByElementId(any())).thenReturn(List.of());

        List<Element> result = elementBusiness.avoirTousElements();
        assertThat(result).hasSize(2);
    }

    @Test
    void traduire_devraitTradureLeLabelEtSesActions() throws ParcoursException {
        Action action = Action.builder().id("a1").nom("Modifier").build();
        Element element = Element.builder().id("e1").nom("Rechercher").action(List.of(action)).build();
        when(traductionService.traduire("Rechercher", "EN")).thenReturn("Search");
        when(actionBusiness.traduire(action, "EN"))
                .thenReturn(Action.builder().id("a1").nom("Update").build());

        Element result = elementBusiness.traduire(element, "EN");

        assertThat(result.getNom()).isEqualTo("Search");
        assertThat(result.getAction().get(0).getNom()).isEqualTo("Update");
    }

    @Test
    void traduire_neDevraitPasTradureSiLangueFr() throws ParcoursException {
        Element element = Element.builder().id("e1").nom("Créer").build();
        Element result = elementBusiness.traduire(element, "fr");
        assertThat(result.getNom()).isEqualTo("Créer");
        verify(traductionService, never()).traduire(any(), any());
    }

    @Test
    void supprimerElement_devraitLeverExceptionSiInexistant() {
        when(elementRepository.existsById("x")).thenReturn(false);
        assertThatThrownBy(() -> elementBusiness.supprimerElement("x"))
                .isInstanceOf(ParcoursException.class);
    }
}
