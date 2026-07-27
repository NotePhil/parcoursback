package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.RoleEntity;
import cmr.notep.login.dao.UtilisateurEntity;
import cmr.notep.login.dao.UtilisateurRoleEntity;
import cmr.notep.login.modele.Utilisateur;
import cmr.notep.login.repository.RoleRepository;
import cmr.notep.login.repository.UtilisateurRepository;
import cmr.notep.login.repository.UtilisateurRoleRepository;
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
class UtilisateurBusinessTest {

    @Mock UtilisateurRepository utilisateurRepository;
    @Mock UtilisateurRoleRepository utilisateurRoleRepository;
    @Mock RoleRepository roleRepository;
    @InjectMocks UtilisateurBusiness utilisateurBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    private UtilisateurEntity utilisateurEntity(String id, String login) {
        UtilisateurEntity e = new UtilisateurEntity();
        e.setId(id);
        e.setLogin(login);
        e.setNom("Dupont");
        e.setPrenom("Jean");
        return e;
    }

    @Test
    void avoirUtilisateur_devraitRetournerUtilisateurAvecRoles() throws ParcoursException {
        UtilisateurEntity entity = utilisateurEntity("u1", "jean@test.fr");
        UtilisateurRoleEntity ur = new UtilisateurRoleEntity();
        ur.setId("ur1"); ur.setUtilisateurId("u1"); ur.setRoleId("r1"); ur.setStatus(true);
        RoleEntity role = new RoleEntity(); role.setId("r1"); role.setTitre("admin");

        when(utilisateurRepository.findById("u1")).thenReturn(Optional.of(entity));
        when(utilisateurRoleRepository.findByUtilisateurId("u1")).thenReturn(List.of(ur));
        when(roleRepository.findById("r1")).thenReturn(Optional.of(role));

        Utilisateur result = utilisateurBusiness.avoirUtilisateur("u1");

        assertThat(result.getNom()).isEqualTo("Dupont");
        assertThat(result.getRoles()).hasSize(1);
        assertThat(result.getRoles().get(0).getRole().getTitre()).isEqualTo("admin");
    }

    @Test
    void avoirUtilisateur_devraitLeverExceptionSiInexistant() {
        when(utilisateurRepository.findById("999")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> utilisateurBusiness.avoirUtilisateur("999"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void posterUtilisateur_devraitPersister() throws ParcoursException {
        UtilisateurEntity entity = utilisateurEntity("u1", "user@test.fr");
        when(utilisateurRepository.save(any())).thenReturn(entity);
        when(utilisateurRoleRepository.findByUtilisateurId("u1")).thenReturn(List.of());

        Utilisateur result = utilisateurBusiness.posterUtilisateur(
                cmr.notep.login.modele.Utilisateur.builder().nom("Dupont").build());

        assertThat(result).isNotNull();
    }

    @Test
    void supprimerUtilisateur_devraitLeverExceptionSiInexistant() {
        when(utilisateurRepository.existsById("x")).thenReturn(false);
        assertThatThrownBy(() -> utilisateurBusiness.supprimerUtilisateur("x"))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void avoirTousUtilisateurs_devraitRetournerListe() {
        when(utilisateurRepository.findAll()).thenReturn(
                List.of(utilisateurEntity("u1", "a@a.fr"), utilisateurEntity("u2", "b@b.fr")));
        when(utilisateurRoleRepository.findByUtilisateurId(any())).thenReturn(List.of());

        List<Utilisateur> result = utilisateurBusiness.avoirTousUtilisateurs();
        assertThat(result).hasSize(2);
    }
}
