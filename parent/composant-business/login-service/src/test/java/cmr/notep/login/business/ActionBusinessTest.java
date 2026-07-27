package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.ActionEntity;
import cmr.notep.login.modele.Action;
import cmr.notep.login.repository.ActionRepository;
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
class ActionBusinessTest {

    @Mock ActionRepository actionRepository;
    @Mock TraductionService traductionService;
    @InjectMocks ActionBusiness actionBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    @Test
    void posterAction_devraitSauvegarderEtRetourner() throws ParcoursException {
        ActionEntity entity = new ActionEntity();
        entity.setId("1");
        entity.setNom("Créer");
        entity.setLien("/creer");
        entity.setBouton("true");
        when(actionRepository.save(any())).thenReturn(entity);

        Action result = actionBusiness.posterAction(Action.builder().nom("Créer").lien("/creer").bouton("true").build());

        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Créer");
        verify(actionRepository, times(1)).save(any());
    }

    @Test
    void avoirAction_devraitRetournerActionExistante() throws ParcoursException {
        ActionEntity entity = new ActionEntity();
        entity.setId("1");
        entity.setNom("Modifier");
        when(actionRepository.findById("1")).thenReturn(Optional.of(entity));

        Action result = actionBusiness.avoirAction("1");

        assertThat(result.getNom()).isEqualTo("Modifier");
    }

    @Test
    void avoirAction_devraitLeverExceptionSiInexistant() {
        when(actionRepository.findById("999")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> actionBusiness.avoirAction("999"))
                .isInstanceOf(ParcoursException.class)
                .hasMessageContaining("999");
    }

    @Test
    void avoirToutesActions_devraitRetournerListe() {
        ActionEntity e1 = new ActionEntity(); e1.setId("1"); e1.setNom("A1");
        ActionEntity e2 = new ActionEntity(); e2.setId("2"); e2.setNom("A2");
        when(actionRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Action> result = actionBusiness.avoirToutesActions();

        assertThat(result).hasSize(2);
    }

    @Test
    void modifierAction_devraitMettreAJourLesChamps() throws ParcoursException {
        ActionEntity entity = new ActionEntity();
        entity.setId("1");
        entity.setNom("Ancien");
        when(actionRepository.findById("1")).thenReturn(Optional.of(entity));
        when(actionRepository.save(any())).thenReturn(entity);

        Action updated = actionBusiness.modifierAction("1", Action.builder().nom("Nouveau").lien("/nouveau").bouton("false").build());

        assertThat(updated).isNotNull();
        verify(actionRepository).save(any());
    }

    @Test
    void supprimerAction_devraitSupprimerSiExistant() throws ParcoursException {
        when(actionRepository.existsById("1")).thenReturn(true);

        actionBusiness.supprimerAction("1");

        verify(actionRepository).deleteById("1");
    }

    @Test
    void supprimerAction_devraitLeverExceptionSiInexistant() {
        when(actionRepository.existsById("999")).thenReturn(false);
        assertThatThrownBy(() -> actionBusiness.supprimerAction("999"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void traduire_neDevraitPasTradureSiLangueFr() throws ParcoursException {
        Action action = Action.builder().id("1").nom("Créer").build();
        Action result = actionBusiness.traduire(action, "fr");
        assertThat(result.getNom()).isEqualTo("Créer");
        verify(traductionService, never()).traduire(any(), any());
    }

    @Test
    void traduire_devraitTradureSiLangueDifferente() throws ParcoursException {
        Action action = Action.builder().id("1").nom("Créer").build();
        when(traductionService.traduire("Créer", "EN")).thenReturn("Create");

        Action result = actionBusiness.traduire(action, "EN");

        assertThat(result.getNom()).isEqualTo("Create");
    }
}
