package cmr.notep.login.impl;

import cmr.notep.login.business.GroupeBusiness;
import cmr.notep.login.modele.Groupe;
import cmr.notep.login.modele.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupeService.class)
class GroupeServiceIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean GroupeBusiness groupeBusiness;

    private Groupe groupeAdminAvecMenu() {
        return Groupe.builder()
                .id("g1").libelle("admin").etat("actif")
                .menu(Menu.builder().langue("fr").fonctionnalites(List.of()).build())
                .build();
    }

    @Test
    void posterGroupe_devraitRetourner200() throws Exception {
        when(groupeBusiness.posterGroupe(any())).thenReturn(groupeAdminAvecMenu());

        mockMvc.perform(post("/login/groupes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Groupe.builder().libelle("admin").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libelle").value("admin"));
    }

    @Test
    void avoirGroupe_devraitRetourner200AvecMenu() throws Exception {
        when(groupeBusiness.avoirGroupe("g1")).thenReturn(groupeAdminAvecMenu());

        mockMvc.perform(get("/login/groupes/g1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("g1"))
                .andExpect(jsonPath("$.menu.langue").value("fr"));
    }

    @Test
    void avoirTousGroupes_devraitRetournerListe() throws Exception {
        when(groupeBusiness.avoirTousGroupes()).thenReturn(List.of(groupeAdminAvecMenu()));

        mockMvc.perform(get("/login/groupes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].libelle").value("admin"));
    }

    @Test
    void modifierGroupe_devraitRetourner200() throws Exception {
        when(groupeBusiness.modifierGroupe(eq("g1"), any())).thenReturn(groupeAdminAvecMenu());

        mockMvc.perform(put("/login/groupes/g1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Groupe.builder().libelle("admin").build())))
                .andExpect(status().isOk());
    }

    @Test
    void supprimerGroupe_devraitRetourner200() throws Exception {
        doNothing().when(groupeBusiness).supprimerGroupe("g1");

        mockMvc.perform(delete("/login/groupes/g1"))
                .andExpect(status().isOk());
    }
}
