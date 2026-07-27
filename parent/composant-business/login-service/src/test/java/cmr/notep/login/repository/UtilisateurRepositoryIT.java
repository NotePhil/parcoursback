package cmr.notep.login.repository;

import cmr.notep.login.dao.GroupeEntity;
import cmr.notep.login.dao.UtilisateurEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class UtilisateurRepositoryIT {

    @Autowired UtilisateurRepository utilisateurRepository;
    @Autowired GroupeRepository groupeRepository;

    @Test
    void sauvegarderEtRetrouvezUtilisateurParLogin() {
        GroupeEntity groupe = new GroupeEntity();
        groupe.setLibelle("admin"); groupe.setEtat("actif");
        GroupeEntity savedGroupe = groupeRepository.save(groupe);

        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setLogin("test@test.fr");
        utilisateur.setPassword("pwd123");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setGroupeId(savedGroupe.getId());
        utilisateurRepository.save(utilisateur);

        Optional<UtilisateurEntity> found = utilisateurRepository.findByLogin("test@test.fr");
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Dupont");
    }

    @Test
    void findByLoginAndPassword_devraitRetournerUtilisateurSiCorrect() {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setLogin("user@domain.fr");
        utilisateur.setPassword("secret");
        utilisateur.setNom("Martin");
        utilisateurRepository.save(utilisateur);

        Optional<UtilisateurEntity> found = utilisateurRepository.findByLoginAndPassword("user@domain.fr", "secret");
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Martin");
    }

    @Test
    void findByLoginAndPassword_devraitRetournerVideSiMotDePasseIncorrect() {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setLogin("user2@domain.fr");
        utilisateur.setPassword("correct");
        utilisateurRepository.save(utilisateur);

        Optional<UtilisateurEntity> found = utilisateurRepository.findByLoginAndPassword("user2@domain.fr", "mauvais");
        assertThat(found).isEmpty();
    }
}
