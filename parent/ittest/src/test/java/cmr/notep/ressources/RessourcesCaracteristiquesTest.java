package cmr.notep.ressources;

import cmr.notep.api.IAttributsApi;
import cmr.notep.api.IFamillesApi;
import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Caracteristiques;
import cmr.notep.modele.Familles;
import cmr.notep.modele.Ressources;
import cmr.notep.modele.TypeAttribut;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Tests d'intégration sur les caractéristiques d'une ressource, la synchronisation
 * de la famille associée, et la protection de la quantité contre toute modification directe.
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class RessourcesCaracteristiquesTest extends AbstractIttest {

    @Autowired
    IAttributsApi attributsApi;

    @Autowired
    IFamillesApi famillesApi;

    /**
     * Test 1 : Enregistrement d'une ressource avec 2 caractéristiques basées sur des attributs existants.
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreerRessourceAvecDeuxCaracteristiques() {
        Attributs attribut1 = attributsApi.posterAttribut(Attributs.builder()
                .titre("Couleur")
                .description("Couleur de la ressource")
                .etat(true)
                .type_attribut(TypeAttribut.Text)
                .dateCreation(new Date())
                .build());

        Attributs attribut2 = attributsApi.posterAttribut(Attributs.builder()
                .titre("Poids")
                .description("Poids de la ressource")
                .etat(true)
                .type_attribut(TypeAttribut.Number)
                .dateCreation(new Date())
                .build());

        Ressources ressource = Ressources.builder()
                .libelle("Ressource Avec Caracteristiques")
                .description("Ressource pour test des caractéristiques")
                .etat(true)
                .quantite(50)
                .seuilAlerte(5)
                .prixEntree(10.0)
                .prixSortie(15.0)
                .unite("PCS")
                .dateCreation(new Date())
                .caracteristiques(List.of(
                        Caracteristiques.builder()
                                .attributs(attribut1)
                                .valeur("Rouge")
                                .build(),
                        Caracteristiques.builder()
                                .attributs(attribut2)
                                .valeur("2.5")
                                .build()
                ))
                .build();

        Ressources saved = ressourceService.posterRessources(ressource);

        Assertions.assertNotNull(saved.getId(), "La ressource doit être créée");
        Assertions.assertNotNull(saved.getCaracteristiques(), "Les caractéristiques ne doivent pas être nulles");
        Assertions.assertEquals(2, saved.getCaracteristiques().size(), "Doit avoir 2 caractéristiques");
        Assertions.assertTrue(saved.getCaracteristiques().stream()
                        .anyMatch(c -> attribut1.getId().equals(c.getAttributs().getId()) && "Rouge".equals(c.getValeur())),
                "La caractéristique 'Couleur' doit être présente");
        Assertions.assertTrue(saved.getCaracteristiques().stream()
                        .anyMatch(c -> attribut2.getId().equals(c.getAttributs().getId()) && "2.5".equals(c.getValeur())),
                "La caractéristique 'Poids' doit être présente");
        log.info("✓ Test 1 réussi : Ressource créée avec 2 caractéristiques");
    }

    /**
     * Test 2 : Modification des caractéristiques existantes et de la famille associée.
     * Vérifie également que la quantité n'est pas modifiable via posterRessources après création.
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testModifierCaracteristiquesEtFamille() {
        Attributs attribut1 = attributsApi.posterAttribut(Attributs.builder()
                .titre("Taille")
                .description("Taille de la ressource")
                .etat(true)
                .type_attribut(TypeAttribut.Text)
                .dateCreation(new Date())
                .build());

        Attributs attribut2 = attributsApi.posterAttribut(Attributs.builder()
                .titre("Matiere")
                .description("Matière de la ressource")
                .etat(true)
                .type_attribut(TypeAttribut.Text)
                .dateCreation(new Date())
                .build());

        Familles familleInitiale = famillesApi.posterFamille(Familles.builder()
                .libelle("Famille Initiale")
                .description("Famille avant modification")
                .etat("true")
                .dateCreation(new Date())
                .build());

        Familles nouvelleFamille = famillesApi.posterFamille(Familles.builder()
                .libelle("Nouvelle Famille")
                .description("Famille après modification")
                .etat("true")
                .dateCreation(new Date())
                .build());

        // 1. Création avec famille initiale + 2 caractéristiques
        Ressources ressource = ressourceService.posterRessources(Ressources.builder()
                .libelle("Ressource Modifiable")
                .description("Ressource pour test de modification")
                .etat(true)
                .quantite(20)
                .seuilAlerte(2)
                .prixEntree(5.0)
                .prixSortie(8.0)
                .unite("PCS")
                .dateCreation(new Date())
                .famille(Familles.builder().id(familleInitiale.getId()).build())
                .caracteristiques(List.of(
                        Caracteristiques.builder().attributs(attribut1).valeur("M").build(),
                        Caracteristiques.builder().attributs(attribut2).valeur("Coton").build()
                ))
                .build());

        Assertions.assertEquals(2, ressource.getCaracteristiques().size());
        Assertions.assertEquals(familleInitiale.getId(), ressource.getFamille().getId());
        Assertions.assertEquals(20, ressource.getQuantite());

        // 2. Modification : nouvelle valeur de caractéristiques + nouvelle famille + tentative de modification de quantité
        ressource.setFamille(Familles.builder().id(nouvelleFamille.getId()).build());
        ressource.setCaracteristiques(List.of(
                Caracteristiques.builder().attributs(attribut1).valeur("L").build(),
                Caracteristiques.builder().attributs(attribut2).valeur("Laine").build()
        ));
        ressource.setQuantite(9999); // Tentative interdite : ne doit pas être prise en compte

        Ressources updated = ressourceService.posterRessources(ressource);

        Assertions.assertEquals(nouvelleFamille.getId(), updated.getFamille().getId(),
                "La famille doit être mise à jour");
        Assertions.assertEquals(2, updated.getCaracteristiques().size(),
                "Doit toujours avoir 2 caractéristiques après modification");
        Assertions.assertTrue(updated.getCaracteristiques().stream()
                        .anyMatch(c -> attribut1.getId().equals(c.getAttributs().getId()) && "L".equals(c.getValeur())),
                "La caractéristique 'Taille' doit être mise à jour à 'L'");
        Assertions.assertTrue(updated.getCaracteristiques().stream()
                        .anyMatch(c -> attribut2.getId().equals(c.getAttributs().getId()) && "Laine".equals(c.getValeur())),
                "La caractéristique 'Matiere' doit être mise à jour à 'Laine'");

        // 3. Vérification cruciale : la quantité n'a pas changé malgré la tentative
        Assertions.assertEquals(20, updated.getQuantite(),
                "La quantité ne doit jamais être modifiable via posterRessources après création");

        Ressources relue = ressourceService.avoirRessource(updated.getId());
        Assertions.assertEquals(20, relue.getQuantite(),
                "La quantité en base doit rester inchangée");

        log.info("✓ Test 2 réussi : caractéristiques et famille mises à jour, quantité protégée");
    }
}

