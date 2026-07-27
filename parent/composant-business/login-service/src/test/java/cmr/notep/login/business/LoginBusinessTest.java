package cmr.notep.login.business;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.dao.UtilisateurEntity;
import cmr.notep.login.gateway.PersonnelGatewayStrategy;
import cmr.notep.login.modele.LoginRequest;
import cmr.notep.login.modele.LoginResponse;
import cmr.notep.login.modele.Utilisateur;
import cmr.notep.login.repository.UtilisateurRepository;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginBusinessTest {

    @Mock UtilisateurRepository utilisateurRepository;
    @Mock UtilisateurBusiness utilisateurBusiness;
    @Mock GroupeBusiness groupeBusiness;
    @Mock PersonnelGatewayStrategy personnelGatewayStrategy;
    @InjectMocks LoginBusiness loginBusiness;

    @BeforeEach
    void setup() {
        dozerMapperBean = new DozerBeanMapper();
    }

    @Test
    void authentifier_devraitRetournerResponseSiIdentifiantsCorrects() throws ParcoursException {
        UtilisateurEntity entity = new UtilisateurEntity();
        entity.setId("u1");
        entity.setLogin("user@test.fr");
        entity.setPassword("pwd123");
        entity.setGroupeId("g1");

        when(utilisateurRepository.findByLoginAndPassword("user@test.fr", "pwd123"))
                .thenReturn(Optional.of(entity));
        when(utilisateurBusiness.avoirUtilisateur("u1"))
                .thenReturn(Utilisateur.builder().id("u1").nom("Durand").build());
        when(groupeBusiness.avoirGroupeAvecTraduction("g1", "fr"))
                .thenReturn(cmr.notep.login.modele.Groupe.builder().id("g1").libelle("admin").build());

        LoginResponse response = loginBusiness.authentifier(
                LoginRequest.builder().login("user@test.fr").passWord("pwd123").langue("fr").build());

        assertThat(response).isNotNull();
        assertThat(response.getLogin()).isEqualTo("user@test.fr");
        assertThat(response.getGroupe()).isNotNull();
        assertThat(response.getUser().getNom()).isEqualTo("Durand");
    }

    @Test
    void authentifier_devraitLeverExceptionSiLoginVide() {
        assertThatThrownBy(() -> loginBusiness.authentifier(
                LoginRequest.builder().login("").passWord("pwd").build()))
                .isInstanceOf(ParcoursException.class)
                .hasMessageContaining("obligatoires");
    }

    @Test
    void authentifier_devraitLeverExceptionSiPasswordVide() {
        assertThatThrownBy(() -> loginBusiness.authentifier(
                LoginRequest.builder().login("user@test.fr").passWord("").build()))
                .isInstanceOf(ParcoursException.class);
    }

    @Test
    void authentifier_devraitLeverExceptionSiIdentifiantsIncorrects() {
        when(utilisateurRepository.findByLoginAndPassword(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginBusiness.authentifier(
                LoginRequest.builder().login("bad@test.fr").passWord("wrong").build()))
                .isInstanceOf(ParcoursException.class)
                .hasMessageContaining("Identifiants incorrects");
    }

    @Test
    void authentifier_devraitTradureMenuSiLangueDifferente() throws ParcoursException {
        UtilisateurEntity entity = new UtilisateurEntity();
        entity.setId("u2");
        entity.setLogin("user@test.fr");
        entity.setPassword("pwd");
        entity.setGroupeId("g1");

        when(utilisateurRepository.findByLoginAndPassword("user@test.fr", "pwd"))
                .thenReturn(Optional.of(entity));
        when(utilisateurBusiness.avoirUtilisateur("u2"))
                .thenReturn(Utilisateur.builder().id("u2").build());
        when(groupeBusiness.avoirGroupeAvecTraduction("g1", "EN"))
                .thenReturn(cmr.notep.login.modele.Groupe.builder().id("g1").build());

        LoginResponse response = loginBusiness.authentifier(
                LoginRequest.builder().login("user@test.fr").passWord("pwd").langue("EN").build());

        verify(groupeBusiness).avoirGroupeAvecTraduction("g1", "EN");
        assertThat(response).isNotNull();
    }

    @Test
    void authentifier_devraitFonctionnerSansGroupe() throws ParcoursException {
        UtilisateurEntity entity = new UtilisateurEntity();
        entity.setId("u3");
        entity.setLogin("user@test.fr");
        entity.setPassword("pwd");
        // pas de groupeId

        when(utilisateurRepository.findByLoginAndPassword("user@test.fr", "pwd"))
                .thenReturn(Optional.of(entity));
        when(utilisateurBusiness.avoirUtilisateur("u3"))
                .thenReturn(Utilisateur.builder().id("u3").build());

        LoginResponse response = loginBusiness.authentifier(
                LoginRequest.builder().login("user@test.fr").passWord("pwd").build());

        assertThat(response.getGroupe()).isNull();
        verifyNoInteractions(groupeBusiness);
    }
}
