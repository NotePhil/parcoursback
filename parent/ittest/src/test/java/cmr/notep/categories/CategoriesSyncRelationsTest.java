package cmr.notep.categories;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.CategoryService;
import cmr.notep.modele.Associer;
import cmr.notep.modele.AssocierId;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Categories;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Tests de synchronisation des relations de Categories (attributs/Associer).
 * Valide les règles métier :
 * - liste absente => purge complète des associations
 * - création/update des associations
 * - suppression des associations non présentes
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_COMMITTED)
@Slf4j
public class CategoriesSyncRelationsTest extends AbstractIttest {

    @Autowired
    CategoryService categoriesService;
    /**
     * Test 1 : Créer une catégorie avec liste vide => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreateCategorieWithAttributsExistAttribut() throws ParcoursException {
        // Récupérer des attributs existants en base
        List<Attributs> attributsExistants = attributService.avoirToutAttribut();
        Assertions.assertTrue(attributsExistants.size() >= 3,
                "Il doit exister au moins 3 attributs en base pour ce test");

        Attributs attr1 = attributsExistants.get(0);
        Attributs attr2 = attributsExistants.get(1);
        Attributs attr3 = attributsExistants.get(2);


        Categories categorie = Categories.builder()
                .libelle("Categorie With 3 Existing Attrs")
                .ordre("1")
                .etat(true)
                .dateCreation(new Date())
                .attributs(List.of(
                        Associer.builder()
                                .attribut(attr1)
                                .ordre(1)
                                .obligatoire(false)
                                .build(),
                        Associer.builder()
                                .attribut(attr2)
                                .ordre(2)
                                .obligatoire(true)
                                .build(),
                        Associer.builder()
                                .attribut(attr3)
                                .ordre(3)
                                .obligatoire(false)
                                .build()
                ))
                .build();

        Categories saved = (Categories)categoriesService.posterCategorie(categorie).getBody();
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getAttributs());
        Assertions.assertEquals(3, saved.getAttributs().size(),
                "La catégorie doit avoir 3 attributs");

        // Vérifier que les attributs retournés correspondent bien à ceux insérés
        List<String> titresAttendus = List.of(attr1.getTitre(), attr2.getTitre(), attr3.getTitre());
        List<String> titresObtenus = saved.getAttributs().stream()
                .map(a -> a.getAttribut().getTitre())
                .toList();
        Assertions.assertTrue(titresObtenus.containsAll(titresAttendus),
                "Les titres des attributs retournés doivent correspondre à ceux insérés. Attendus : " + titresAttendus + ", obtenus : " + titresObtenus);

        List<String> idsAttendus = List.of(attr1.getId(), attr2.getId(), attr3.getId());
        List<String> idsObtenus = saved.getAttributs().stream()
                .map(a -> a.getAttribut().getId())
                .toList();
        Assertions.assertTrue(idsObtenus.containsAll(idsAttendus),
                "Les IDs des attributs retournés doivent correspondre à ceux insérés. Attendus : " + idsAttendus + ", obtenus : " + idsObtenus);

        log.info("✓ Test 1 réussi : Catégorie créée avec 3 attributs existants et vérification des libellés OK");
    }

    /**
     * Test 2 : Créer une catégorie avec attributs, puis la supprimer avec tous ses attributs associés
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testDeleteCategoryAndAttributs() throws ParcoursException {
        Attributs att = attributService.avoirToutAttribut().getFirst();
        String attId = att.getId();
        // 1. Créer avec attributs
        Categories categorie = Categories.builder()
                .libelle("Categorie With Attrs To Delete")
                .ordre("2")
                .etat(true)
                .dateCreation(new Date())
                .attributs(List.of(
                        Associer.builder()
                                .attribut(att)
                                .obligatoire(true)
                                .ordre(1)
                                .build()
                ))
                .build();

        Categories saved = (Categories)categoriesService.posterCategorie(categorie).getBody();
        String catId = saved.getId();
        Assertions.assertNotNull(catId, "La catégorie doit avoir un ID");
        Assertions.assertEquals(1, saved.getAttributs().size(),
                "Doit avoir 1 attribut");
        log.info("✓ Catégorie créée avec 1 attribut : {}", saved.getId());

        // 3. Vérifier que la catégorie n'existe plus en base aprés suppression des associations d'attributs
        try {
            saved.getAttributs().clear();
            saved = (Categories)categoriesService.posterCategorie(saved).getBody();
            Categories categoriesToDelete = categoriesService.avoirCategorie(catId);
            Assertions.assertNull(categoriesToDelete.getId(),"La catégorie supprimée ne devrait pas exister en base");

        } catch (RuntimeException e) {
            Assertions.assertTrue(e.getMessage().contains("Categorie inexistante"),
                    "L'exception doit indiquer que la catégorie est inexistante");
            log.info("✓ Catégorie correctement supprimée de la base de données");
        }
        // Vérifier que l'attribut lui-même n'a pas été supprimé (cascade interdite)
        Attributs attToujours = attributService.avoirAttribut(attId);
        Assertions.assertNotNull(attToujours,
                "L'attribut ne doit pas être supprimé lors de la suppression de la catégorie");

        log.info("✓ Test 2 réussi : Catégorie et ses associations supprimées correctement");
    }

    /**
     * Test 3 : Synchronisation partielle des attributs
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testUpdateCategorieWithAttributSync() throws ParcoursException {

        // Simule : client GET /categories
        // avoirToutCategories() retourne des DTO purs (Dozer-mappés, sans contexte JPA)
        // → identique à ce qu'un client reçoit en JSON et désérialise
        Categories categorie = categoriesService.avoirToutCategories().stream()
                .filter(c -> c.getAttributs() != null && c.getAttributs().size() >= 2)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Aucune catégorie avec au moins 2 attributs trouvée en base"));

        Assertions.assertTrue(categorie.getAttributs().size() >= 2,
                "Doit avoir au moins deux attributs");

        // Simule : le client IHM conserve le 1er attribut tel quel (reçu du JSON)
        // Pas besoin de deep copy : l'objet est déjà un DTO pur, sans session JPA
        Associer attrConserve = categorie.getAttributs().get(0);
        String titreAttrConserve = attrConserve.getAttribut().getTitre();

        List<String> listAttributsAvant = categorie.getAttributs().stream()
                .map(a -> a.getAttribut().getId())
                .toList();
        // Simule : le client remplace le 2ème attribut par un autre disponible
        Attributs nouvelAttribut = attributService.avoirToutAttribut().stream()
                .filter(x-> !listAttributsAvant.contains(x.getId()))
                .collect(Collectors.toList()).get(0);
        Associer nouvelAssocier = Associer.builder()
                .id(AssocierId.builder()
                        .attributsId(nouvelAttribut.getId())
                        .build())
                .attribut(nouvelAttribut)
                .build();

        // Simule : client PUT /categories → envoie la catégorie modifiée
        // [attrConserve (1er inchangé), nouvelAssocier (remplace le 2ème)]
        categorie.setAttributs(List.of(attrConserve, nouvelAssocier));

        Categories updated = (Categories) categoriesService.posterCategorie(categorie).getBody();

        Assertions.assertEquals(2, updated.getAttributs().size(),
                "Doit avoir exactement 2 attributs après sync");

        List<String> titresObtenus = updated.getAttributs().stream()
                .map(a -> a.getAttribut().getTitre())
                .toList();

        Assertions.assertTrue(titresObtenus.contains(titreAttrConserve),
                "Le 1er attribut conservé doit toujours être présent. Attendu : "
                        + titreAttrConserve + ", obtenus : " + titresObtenus);
        Assertions.assertTrue(titresObtenus.contains(nouvelAttribut.getTitre()),
                "Le nouvel attribut doit être présent. Attendu : "
                        + nouvelAttribut.getTitre() + ", obtenus : " + titresObtenus);

        log.info("✓ Test 3 réussi : client IHM - 1er attribut conservé '{}', 2ème remplacé par '{}'",
                titreAttrConserve, nouvelAttribut.getTitre());
    }

    /**
     * Test 4 : Purge des attributs lors d'un update avec liste vide
     */
   // @Test
    @Order(4)
    @SneakyThrows
    public void testUpdateCategorieWithEmptyAttributList() throws ParcoursException {
        String catId = UUID.randomUUID().toString();
        String attId = UUID.randomUUID().toString();
        // 1. Créer avec attributs
        Categories categorie = Categories.builder()
                .id(catId)
                .libelle("Categorie To Purge")
                .ordre("4")
                .etat(true)
                .dateCreation(new Date())
                .attributs(List.of(
                        Associer.builder()
                                .id(AssocierId.builder().categoriesId(catId).attributsId(attId).build())
                                .attribut(Attributs.builder().id(attId).build())
                                .build()
                ))
                .build();

        Categories saved = (Categories)categoriesService.posterCategorie(categorie).getBody();
        Assertions.assertEquals(1, saved.getAttributs().size());

        // 2. Mettre à jour avec liste vide
        saved.setAttributs(new ArrayList<>());
        Categories updated = (Categories)categoriesService.posterCategorie(saved).getBody();
        Assertions.assertTrue(updated.getAttributs().isEmpty(),
                "Les attributs doivent être purgés");
        log.info("✓ Test 4 réussi : Purge des attributs avec liste vide réussie");
    }
}
