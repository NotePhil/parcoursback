package cmr.notep.login.repository;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.business.ActionBusiness;
import cmr.notep.login.business.ElementBusiness;
import cmr.notep.login.business.FonctionnaliteBusiness;
import cmr.notep.login.dao.FonctionnaliteEntity;
import cmr.notep.login.dao.GroupeEntity;
import cmr.notep.login.modele.Fonctionnalite;
import cmr.notep.login.traduction.TraductionService;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static cmr.notep.login.config.LoginConfig.dozerMapperBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class FonctionnaliteRepositoryIT {

    @Autowired FonctionnaliteRepository fonctionnaliteRepository;
    @Autowired GroupeRepository groupeRepository;
    @Autowired ElementRepository elementRepository;
    @Autowired ActionRepository actionRepository;

    /**
     * Mock de l'API de traduction : renvoie le même mot sans appel externe.
     * Permet de tester la couche métier en isolation réseau complète.
     */
    @MockBean TraductionService traductionService;

    private FonctionnaliteBusiness fonctionnaliteBusiness;

    @BeforeEach
    void setup() throws ParcoursException {
        dozerMapperBean = new DozerBeanMapper();

        // Mock de traduction : renvoie toujours le texte original
        when(traductionService.traduire(anyString(), anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ActionBusiness actionBusiness = new ActionBusiness(actionRepository, traductionService);
        ElementBusiness elementBusiness = new ElementBusiness(elementRepository, actionRepository, actionBusiness, traductionService);
        fonctionnaliteBusiness = new FonctionnaliteBusiness(fonctionnaliteRepository, elementRepository, elementBusiness, traductionService);
    }

    @Test
    void findByGroupeId_devraitRetournerFonctionnalitesDuGroupe() {
        GroupeEntity groupe = new GroupeEntity();
        groupe.setLibelle("admin"); groupe.setEtat("actif");
        GroupeEntity savedGroupe = groupeRepository.save(groupe);

        FonctionnaliteEntity f1 = new FonctionnaliteEntity();
        f1.setFonction("Personne"); f1.setIcone("fa-user"); f1.setActif("true"); f1.setGroupeId(savedGroupe.getId());
        FonctionnaliteEntity f2 = new FonctionnaliteEntity();
        f2.setFonction("Personnel"); f2.setIcone("fa-user-cog"); f2.setActif("true"); f2.setGroupeId(savedGroupe.getId());

        fonctionnaliteRepository.saveAll(List.of(f1, f2));

        List<FonctionnaliteEntity> result = fonctionnaliteRepository.findByGroupeId(savedGroupe.getId());
        assertThat(result).hasSize(2);
        assertThat(result).extracting(FonctionnaliteEntity::getFonction)
                .containsExactlyInAnyOrder("Personne", "Personnel");
    }

    @Test
    void traduire_avecMockPassThrough_devraitRetournerMemeMotEnAnglais() throws ParcoursException {
        // Arrange : persister une fonctionnalité en français (langue de stockage)
        GroupeEntity groupe = new GroupeEntity();
        groupe.setLibelle("utilisateur"); groupe.setEtat("actif");
        GroupeEntity savedGroupe = groupeRepository.save(groupe);

        FonctionnaliteEntity entity = new FonctionnaliteEntity();
        entity.setFonction("Référentiel");
        entity.setIcone("fa-database");
        entity.setActif("true");
        entity.setGroupeId(savedGroupe.getId());
        fonctionnaliteRepository.save(entity);

        // Act : demande de traduction EN — le mock renvoie le même mot
        Fonctionnalite fonctionnalite = fonctionnaliteBusiness.avoirFonctionnalite(entity.getId());
        Fonctionnalite traduite = fonctionnaliteBusiness.traduire(fonctionnalite, "EN");

        // Assert : le texte est identique car le mock renvoie le mot original
        assertThat(traduite).isNotNull();
        assertThat(traduite.getFonction()).isEqualTo("Référentiel");
        assertThat(traduite.getIcone()).isEqualTo("fa-database");
    }

    @Test
    void traduire_avecMockPassThrough_devraitPreserverIconeEtActif() throws ParcoursException {
        // Arrange
        GroupeEntity groupe = new GroupeEntity();
        groupe.setLibelle("admin"); groupe.setEtat("actif");
        GroupeEntity savedGroupe = groupeRepository.save(groupe);

        FonctionnaliteEntity entity = new FonctionnaliteEntity();
        entity.setFonction("Exemplaire");
        entity.setIcone("fa-book");
        entity.setActif("true");
        entity.setGroupeId(savedGroupe.getId());
        fonctionnaliteRepository.save(entity);

        // Act
        Fonctionnalite fonctionnalite = fonctionnaliteBusiness.avoirFonctionnalite(entity.getId());
        Fonctionnalite traduite = fonctionnaliteBusiness.traduire(fonctionnalite, "ES");

        // Assert : icone et actif non traduits, fonction inchangée via mock
        assertThat(traduite.getIcone()).isEqualTo("fa-book");
        assertThat(traduite.getActif()).isEqualTo("true");
        assertThat(traduite.getFonction()).isEqualTo("Exemplaire");
    }

    @Test
    void traduire_avecLangueFr_neDoitPasAppelerLaMockTraduction() throws ParcoursException {
        // Arrange
        GroupeEntity groupe = new GroupeEntity();
        groupe.setLibelle("admin"); groupe.setEtat("actif");
        GroupeEntity savedGroupe = groupeRepository.save(groupe);

        FonctionnaliteEntity entity = new FonctionnaliteEntity();
        entity.setFonction("Document");
        entity.setIcone("fa-file");
        entity.setActif("true");
        entity.setGroupeId(savedGroupe.getId());
        fonctionnaliteRepository.save(entity);

        // Act : langue fr → aucune traduction déclenchée
        Fonctionnalite fonctionnalite = fonctionnaliteBusiness.avoirFonctionnalite(entity.getId());
        Fonctionnalite resultat = fonctionnaliteBusiness.traduire(fonctionnalite, "fr");

        // Assert : objet retourné tel quel, sans passage par le service de traduction
        assertThat(resultat.getFonction()).isEqualTo("Document");
        assertThat(resultat).isSameAs(fonctionnalite);
    }
}
