package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.GroupeEntity;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.modele.Groupe;
import cmr.notep.login.repository.GroupeRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupeBusinessTest {

    @Mock GroupeRepository groupeRepository;
    @Mock FonctionnaliteBusiness fonctionnaliteBusiness;
    @InjectMocks GroupeBusiness groupeBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    private GroupeEntity groupeEntity(String id, String libelle) {
        GroupeEntity e = new GroupeEntity();
        e.setId(id);
        e.setLibelle(libelle);
        e.setEtat("actif");
        return e;
    }

    @Test
    void avoirGroupe_devraitRetournerGroupeAvecMenu() throws ParcoursException {
        when(groupeRepository.findById("g1")).thenReturn(Optional.of(groupeEntity("g1", "admin")));
        when(fonctionnaliteBusiness.avoirFonctionnalitesDuGroupe("g1")).thenReturn(List.of(
                Fonctionnalite.builder().id("f1").fonction("Personne").build()));

        Groupe result = groupeBusiness.avoirGroupe("g1");

        assertThat(result.getLibelle()).isEqualTo("admin");
        assertThat(result.getMenu()).isNotNull();
        assertThat(result.getMenu().getFonctionnalites()).hasSize(1);
        assertThat(result.getMenu().getLangue()).isEqualTo("fr");
    }

    @Test
    void avoirGroupe_devraitLeverExceptionSiInexistant() {
        when(groupeRepository.findById("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> groupeBusiness.avoirGroupe("x"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void avoirGroupeAvecTraduction_devraitTradureSiLangueDifferente() throws ParcoursException {
        when(groupeRepository.findById("g1")).thenReturn(Optional.of(groupeEntity("g1", "admin")));
        Fonctionnalite f = Fonctionnalite.builder().id("f1").fonction("Personne").build();
        when(fonctionnaliteBusiness.avoirFonctionnalitesDuGroupe("g1")).thenReturn(List.of(f));
        when(fonctionnaliteBusiness.traduire(f, "EN"))
                .thenReturn(Fonctionnalite.builder().id("f1").fonction("Person").build());

        Groupe result = groupeBusiness.avoirGroupeAvecTraduction("g1", "EN");

        assertThat(result.getMenu().getLangue()).isEqualTo("EN");
        assertThat(result.getMenu().getFonctionnalites().get(0).getFonction()).isEqualTo("Person");
    }

    @Test
    void posterGroupe_devraitPersister() throws ParcoursException {
        GroupeEntity entity = groupeEntity("g1", "utilisateur");
        when(groupeRepository.save(any())).thenReturn(entity);

        Groupe result = groupeBusiness.posterGroupe(Groupe.builder().libelle("utilisateur").build());

        assertThat(result.getLibelle()).isEqualTo("utilisateur");
    }

    @Test
    void supprimerGroupe_devraitLeverExceptionSiInexistant() {
        when(groupeRepository.existsById("x")).thenReturn(false);
        assertThatThrownBy(() -> groupeBusiness.supprimerGroupe("x"))
                .isInstanceOf(ParcoursException.class);
    }
}
