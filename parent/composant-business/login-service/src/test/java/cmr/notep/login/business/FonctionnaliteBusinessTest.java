package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.FonctionnaliteEntity;
import cmr.notep.login.modele.Element;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.repository.ElementRepository;
import cmr.notep.login.repository.FonctionnaliteRepository;
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
class FonctionnaliteBusinessTest {

    @Mock FonctionnaliteRepository fonctionnaliteRepository;
    @Mock ElementRepository elementRepository;
    @Mock ElementBusiness elementBusiness;
    @Mock TraductionService traductionService;
    @InjectMocks FonctionnaliteBusiness fonctionnaliteBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    private FonctionnaliteEntity entity(String id, String fonction) {
        FonctionnaliteEntity e = new FonctionnaliteEntity();
        e.setId(id); e.setFonction(fonction); e.setIcone("fa-icon"); e.setActif("true"); e.setGroupeId("g1");
        return e;
    }

    @Test
    void avoirFonctionnalite_devraitRetournerAvecElements() throws ParcoursException {
        FonctionnaliteEntity fe = entity("f1", "Personne");
        when(fonctionnaliteRepository.findById("f1")).thenReturn(Optional.of(fe));
        when(elementRepository.findByFonctionnaliteId("f1")).thenReturn(List.of());
        when(elementBusiness.avoirElement(any())).thenThrow(new ParcoursException(
                cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.NOT_FOUND, "x"));

        Fonctionnalite result = fonctionnaliteBusiness.avoirFonctionnalite("f1");

        assertThat(result.getFonction()).isEqualTo("Personne");
        assertThat(result.getElements()).isEmpty();
    }

    @Test
    void avoirFonctionnalite_devraitLeverExceptionSiInexistante() {
        when(fonctionnaliteRepository.findById("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fonctionnaliteBusiness.avoirFonctionnalite("x"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void posterFonctionnalite_devraitPersister() throws ParcoursException {
        FonctionnaliteEntity saved = entity("f1", "Personnel");
        when(fonctionnaliteRepository.save(any())).thenReturn(saved);

        Fonctionnalite result = fonctionnaliteBusiness.posterFonctionnalite(
                Fonctionnalite.builder().fonction("Personnel").icone("fa-user").actif("true").build());

        assertThat(result.getFonction()).isEqualTo("Personnel");
    }

    @Test
    void avoirFonctionnalitesDuGroupe_devraitFiltrerParGroupe() {
        when(fonctionnaliteRepository.findByGroupeId("g1"))
                .thenReturn(List.of(entity("f1", "Personne"), entity("f2", "Personnel")));
        when(elementRepository.findByFonctionnaliteId(any())).thenReturn(List.of());

        List<Fonctionnalite> result = fonctionnaliteBusiness.avoirFonctionnalitesDuGroupe("g1");

        assertThat(result).hasSize(2);
    }

    @Test
    void traduire_neDevraitPasTradureSiLangueFr() throws ParcoursException {
        Fonctionnalite f = Fonctionnalite.builder().id("f1").fonction("Personne").build();
        Fonctionnalite result = fonctionnaliteBusiness.traduire(f, "fr");
        assertThat(result.getFonction()).isEqualTo("Personne");
        verify(traductionService, never()).traduire(any(), any());
    }

    @Test
    void traduire_devraitTradureSiLangueDifferente() throws ParcoursException {
        Fonctionnalite f = Fonctionnalite.builder().id("f1").fonction("Personne").elements(List.of()).build();
        when(traductionService.traduire("Personne", "EN")).thenReturn("Person");

        Fonctionnalite result = fonctionnaliteBusiness.traduire(f, "EN");

        assertThat(result.getFonction()).isEqualTo("Person");
    }

    @Test
    void supprimerFonctionnalite_devraitLeverExceptionSiInexistante() {
        when(fonctionnaliteRepository.existsById("x")).thenReturn(false);
        assertThatThrownBy(() -> fonctionnaliteBusiness.supprimerFonctionnalite("x"))
                .isInstanceOf(ParcoursException.class);
    }
}
