package cmr.notep.login.impl;

import cmr.notep.login.business.LoginBusiness;
import cmr.notep.login.modele.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginService.class)
class LoginServiceIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean LoginBusiness loginBusiness;

    @Test
    void authentifier_devraitRetourner200AvecMenuSiIdentifiantsValides() throws Exception {
        Utilisateur user = Utilisateur.builder().id("u1").nom("Dombo").prenom("Gilles").build();
        Groupe groupe = Groupe.builder()
                .id("g1").libelle("admin")
                .menu(Menu.builder().langue("fr")
                        .fonctionnalites(List.of(
                                Fonctionnalite.builder().fonction("Personne").icone("fa-user").build()))
                        .build())
                .build();
        LoginResponse response = LoginResponse.builder()
                .id("u1").login("dombogilles@gmail.com")
                .user(user).groupe(groupe).build();

        when(loginBusiness.authentifier(any())).thenReturn(response);

        LoginRequest request = LoginRequest.builder()
                .login("dombogilles@gmail.com").passWord("oijfsdv2fdg3f5").langue("fr").build();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("dombogilles@gmail.com"))
                .andExpect(jsonPath("$.user.nom").value("Dombo"))
                .andExpect(jsonPath("$.groupe.menu.langue").value("fr"))
                .andExpect(jsonPath("$.groupe.menu.fonctionnalites[0].fonction").value("Personne"));
    }

    @Test
    void authentifier_devraitRetourner200AvecMenuEnAnglais() throws Exception {
        Groupe groupe = Groupe.builder()
                .id("g1").libelle("admin")
                .menu(Menu.builder().langue("EN")
                        .fonctionnalites(List.of(
                                Fonctionnalite.builder().fonction("Person").icone("fa-user").build()))
                        .build())
                .build();
        when(loginBusiness.authentifier(any()))
                .thenReturn(LoginResponse.builder().id("u1").login("user@test.fr")
                        .user(Utilisateur.builder().id("u1").build()).groupe(groupe).build());

        LoginRequest request = LoginRequest.builder()
                .login("user@test.fr").passWord("pwd").langue("EN").build();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupe.menu.langue").value("EN"))
                .andExpect(jsonPath("$.groupe.menu.fonctionnalites[0].fonction").value("Person"));
    }

    @Test
    void authentifier_devraitRetourner500SiIdentifiantsInvalides() throws Exception {
        when(loginBusiness.authentifier(any()))
                .thenThrow(new cmr.notep.exceptions.ParcoursException(
                        cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.OPERATION_INTERDITE,
                        "Identifiants incorrects"));

        LoginRequest request = LoginRequest.builder()
                .login("mauvais@test.fr").passWord("wrong").langue("fr").build();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError());
    }
}
