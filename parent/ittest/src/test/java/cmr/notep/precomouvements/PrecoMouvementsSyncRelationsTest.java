package cmr.notep.precomouvements;

import cmr.notep.api.IFamillesApi;
import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.PrecoMouvementsService;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.Familles;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.modele.Ressources;
import cmr.notep.modele.TypeMouvement;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Tests de synchronisation des relations de PrecoMouvements (precoMouvementsQtes).
 * Valide les règles métier :
 * - liste absente => purge complète des quantités
 * - création/update des quantités
 * - suppression des quantités non présentes
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class PrecoMouvementsSyncRelationsTest extends AbstractIttest {
    @Autowired
    PrecoMouvementsService precoMouvementsService;

    @Autowired
    IFamillesApi famillesService;

    /**
     * Test 1 : Créer un précomouvement avec liste vide => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreatePrecoMouvementWithEmptyQuantites() throws ParcoursException {
        PrecoMouvements preco = PrecoMouvements.builder()
                .libelle("Preco Empty Qtes")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(new ArrayList<>()) // liste vide => purge
                .build();

        PrecoMouvements saved = precoMouvementsService.posterPrecoMouvements(preco);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getPrecoMouvementsQtes());
        Assertions.assertTrue(saved.getPrecoMouvementsQtes().isEmpty(),
                "Les quantités doivent être vides");
        log.info("✓ Test 1 réussi : PrecoMouvement créé avec quantités purgées");
    }

    /**
     * Test 2 : Créer un précomouvement avec quantités, puis purger via update
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testUpdatePrecoMouvementWithQuantitePurge() throws ParcoursException {
        // 1. Créer avec quantités
        PrecoMouvements preco = PrecoMouvements.builder()
                .libelle("Preco With Qtes")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .quantiteMin(1)
                                .quantiteMax(10)
                                .montantMin(100.0)
                                .montantMax(1000.0)
                                .dateCreation(new Date())
                                .build()
                ))
                .build();

        PrecoMouvements saved = precoMouvementsService.posterPrecoMouvements(preco);
        Assertions.assertEquals(1, saved.getPrecoMouvementsQtes().size(),
                "Doit avoir 1 quantité initialement");

        // 2. Purger via update avec liste null
        saved.setPrecoMouvementsQtes(null);
        PrecoMouvements updated = precoMouvementsService.posterPrecoMouvements(saved);
        Assertions.assertTrue(updated.getPrecoMouvementsQtes() == null ||
                updated.getPrecoMouvementsQtes().isEmpty(),
                "Les quantités doivent être purgées");
        log.info("✓ Test 2 réussi : PrecoMouvement mis à jour avec purge des quantités");
    }

    /**
     * Test 3 : Synchronisation partielle de quantités (add/remove/keep)
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testUpdatePrecoMouvementWithQuantiteSync() throws ParcoursException {
        // 1. Créer avec 2 quantités
        PrecoMouvements preco = PrecoMouvements.builder()
                .libelle("Preco Sync Qtes")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .quantiteMin(1)
                                .quantiteMax(10)
                                .montantMin(100.0)
                                .montantMax(1000.0)
                                .dateCreation(new Date())
                                .build(),
                        PrecoMouvementsQtes.builder()
                                .quantiteMin(11)
                                .quantiteMax(20)
                                .montantMin(1001.0)
                                .montantMax(2000.0)
                                .dateCreation(new Date())
                                .build()
                ))
                .build();

        PrecoMouvements saved = precoMouvementsService.posterPrecoMouvements(preco);
        Assertions.assertEquals(2, saved.getPrecoMouvementsQtes().size(),
                "Doit avoir 2 quantités initialement");

        // 2. Sync : garder 1ère, supprimer 2ème, ajouter 3ème
        String qte1Id = saved.getPrecoMouvementsQtes().getFirst().getId();
        saved.setPrecoMouvementsQtes(List.of(
                PrecoMouvementsQtes.builder()
                        .id(qte1Id)  // existing => update
                        .quantiteMin(2)
                        .quantiteMax(15)
                        .montantMin(150.0)
                        .montantMax(1500.0)
                        .dateCreation(new Date())
                        .build(),
                PrecoMouvementsQtes.builder()
                        .quantiteMin(21)
                        .quantiteMax(30)
                        .montantMin(2001.0)
                        .montantMax(3000.0)
                        .dateCreation(new Date())
                        .build()
        ));

        PrecoMouvements updated = precoMouvementsService.posterPrecoMouvements(saved);
        Assertions.assertEquals(2, updated.getPrecoMouvementsQtes().size(),
                "Doit avoir 2 quantités après sync");
        log.info("✓ Test 3 réussi : Synchronisation partielle des quantités réussie");
    }

    /**
     * Test 4 : Erreur si quantité inexistante lors de l'update
     */
    @Test
    @Order(4)
    @SneakyThrows
    public void testUpdatePrecoMouvementWithInvalidQuantite() {
        PrecoMouvements created = precoMouvementsService.posterPrecoMouvements(PrecoMouvements.builder()
                .libelle("Preco Invalid Qte")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .quantiteMin(1)
                                .quantiteMax(10)
                                .montantMin(100.0)
                                .montantMax(1000.0)
                                .dateCreation(new Date())
                                .build()
                ))
                .build());

        created.setPrecoMouvementsQtes(List.of(
                PrecoMouvementsQtes.builder()
                        .id("invalid-qte-id")  // ID inexistant sur update
                        .quantiteMin(2)
                        .quantiteMax(12)
                        .montantMin(200.0)
                        .montantMax(1200.0)
                        .dateCreation(new Date())
                        .build()
        ));

        Assertions.assertThrows(RuntimeException.class,
                () -> precoMouvementsService.posterPrecoMouvements(created),
                "Doit lancer une exception si la quantité n'existe pas");
        log.info("✓ Test 4 réussi : Exception levée pour quantité inexistante");
    }

    /**
     * Test 5 : Création d'un précomouvement dont la quantité référence ressource + familles + distributeurs existants
     */
    @Test
    @Order(5)
    @SneakyThrows
    public void testCreatePrecoMouvementWithQuantiteAndRelations() throws ParcoursException {
        Familles familleSaved = famillesService.posterFamille(Familles.builder()
                .libelle("Famille Preco Qte")
                .description("Famille pour test de liaison")
                .etat("true")
                .dateCreation(new Date())
                .build());

        Distributeurs distributeurSaved = creerDistributeur("DIST-PRECO-001", "dist.preco.001@test.fr");

        Ressources ressourceSaved = ressourceService.posterRessources(Ressources.builder()
                .libelle("Ressource Preco Qte")
                .description("Ressource pour test de liaison")
                .etat(true)
                .quantite(100)
                .seuilAlerte(10)
                .prixEntree(1200.0)
                .prixSortie(1500.0)
                .unite("PCS")
                .famille(familleSaved)
                .dateCreation(new Date())
                .build());

        PrecoMouvements preco = PrecoMouvements.builder()
                .libelle("Preco Qte With Relations")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .quantiteMin(5)
                                .quantiteMax(25)
                                .montantMin(500.0)
                                .montantMax(2500.0)
                                .ressource(ressourceSaved)
                                .familles(List.of(familleSaved))
                                .distributeurs(List.of(distributeurSaved))
                                .dateCreation(new Date())
                                .build()
                ))
                .build();

        PrecoMouvements saved = precoMouvementsService.posterPrecoMouvements(preco);
        Assertions.assertNotNull(saved.getId(), "Le précomouvement doit être créé");
        Assertions.assertNotNull(saved.getPrecoMouvementsQtes(), "Les quantités ne doivent pas être nulles");
        Assertions.assertEquals(1, saved.getPrecoMouvementsQtes().size(), "Doit avoir 1 quantité");

        PrecoMouvementsQtes savedQte = saved.getPrecoMouvementsQtes().getFirst();
        Assertions.assertNotNull(savedQte.getRessource(), "La ressource doit être liée sur la quantité");
        Assertions.assertEquals(ressourceSaved.getId(), savedQte.getRessource().getId(),
                "La ressource liée doit correspondre à celle créée");
        Assertions.assertNotNull(savedQte.getFamilles(), "Les familles doivent être présentes sur la quantité");
        Assertions.assertTrue(savedQte.getFamilles().stream().anyMatch(f -> familleSaved.getId().equals(f.getId())),
                "La famille liée doit correspondre à celle créée");
        Assertions.assertNotNull(savedQte.getDistributeurs(), "Les distributeurs doivent être présents sur la quantité");
        Assertions.assertTrue(savedQte.getDistributeurs().stream().anyMatch(d -> distributeurSaved.getId().equals(d.getId())),
                "Le distributeur lié doit correspondre à celui créé");
        log.info("✓ Test 5 réussi : PrecoMouvement créé avec quantité liée à ressource/familles/distributeurs");
    }

    /**
     * Test 6 : Purger les liens familles/distributeurs via null sans supprimer la quantité ni la ressource
     */
    @Test
    @Order(6)
    @SneakyThrows
    public void testUpdatePrecoMouvementWithNullRelationsKeepsQuantiteAndRessource() throws ParcoursException {
        PrecoMouvements saved = creerPrecoAvecToutesRelations("DIST-PRECO-NULL", "dist.preco.null@test.fr");
        PrecoMouvementsQtes qteInitiale = saved.getPrecoMouvementsQtes().getFirst();

        saved.setPrecoMouvementsQtes(List.of(PrecoMouvementsQtes.builder()
                .id(qteInitiale.getId())
                .quantiteMin(qteInitiale.getQuantiteMin())
                .quantiteMax(qteInitiale.getQuantiteMax())
                .montantMin(qteInitiale.getMontantMin())
                .montantMax(qteInitiale.getMontantMax())
                .ressource(qteInitiale.getRessource())
                .familles(null)
                .distributeurs(null)
                .dateCreation(qteInitiale.getDateCreation())
                .build()));

        PrecoMouvements updated = precoMouvementsService.posterPrecoMouvements(saved);

        Assertions.assertEquals(1, updated.getPrecoMouvementsQtes().size(), "La quantité doit être conservée");
        PrecoMouvementsQtes updatedQte = updated.getPrecoMouvementsQtes().getFirst();
        Assertions.assertEquals(qteInitiale.getId(), updatedQte.getId(), "La même quantité doit être conservée");
        Assertions.assertNotNull(updatedQte.getRessource(), "La ressource doit rester liée");
        Assertions.assertEquals(qteInitiale.getRessource().getId(), updatedQte.getRessource().getId(),
                "La ressource liée doit être conservée");
        Assertions.assertTrue(updatedQte.getFamilles() == null || updatedQte.getFamilles().isEmpty(),
                "Les liens familles doivent être purgés");
        Assertions.assertTrue(updatedQte.getDistributeurs() == null || updatedQte.getDistributeurs().isEmpty(),
                "Les liens distributeurs doivent être purgés");
        log.info("✓ Test 6 réussi : purge par null sans suppression de la quantité ni de la ressource");
    }

    /**
     * Test 7 : Purger les liens familles/distributeurs via listes vides sans supprimer la quantité ni la ressource
     */
    @Test
    @Order(7)
    @SneakyThrows
    public void testUpdatePrecoMouvementWithEmptyRelationsKeepsQuantiteAndRessource() throws ParcoursException {
        PrecoMouvements saved = creerPrecoAvecToutesRelations("DIST-PRECO-EMPTY", "dist.preco.empty@test.fr");
        PrecoMouvementsQtes qteInitiale = saved.getPrecoMouvementsQtes().getFirst();

        saved.setPrecoMouvementsQtes(List.of(PrecoMouvementsQtes.builder()
                .id(qteInitiale.getId())
                .quantiteMin(qteInitiale.getQuantiteMin())
                .quantiteMax(qteInitiale.getQuantiteMax())
                .montantMin(qteInitiale.getMontantMin())
                .montantMax(qteInitiale.getMontantMax())

                .ressource(qteInitiale.getRessource())
                .familles(new ArrayList<>())
                .distributeurs(new ArrayList<>())
                .dateCreation(qteInitiale.getDateCreation())
                .build()));

        PrecoMouvements updated = precoMouvementsService.posterPrecoMouvements(saved);

        Assertions.assertEquals(1, updated.getPrecoMouvementsQtes().size(), "La quantité doit être conservée");
        PrecoMouvementsQtes updatedQte = updated.getPrecoMouvementsQtes().getFirst();
        Assertions.assertEquals(qteInitiale.getId(), updatedQte.getId(), "La même quantité doit être conservée");
        Assertions.assertNotNull(updatedQte.getRessource(), "La ressource doit rester liée");
        Assertions.assertEquals(qteInitiale.getRessource().getId(), updatedQte.getRessource().getId(),
                "La ressource liée doit être conservée");
        Assertions.assertTrue(updatedQte.getFamilles() == null || updatedQte.getFamilles().isEmpty(),
                "Les liens familles doivent être purgés");
        Assertions.assertTrue(updatedQte.getDistributeurs() == null || updatedQte.getDistributeurs().isEmpty(),
                "Les liens distributeurs doivent être purgés");
        log.info("✓ Test 7 réussi : purge par listes vides sans suppression de la quantité ni de la ressource");
    }

    private PrecoMouvements creerPrecoAvecToutesRelations(String codeDistributeur,
                                                          String mailDistributeur) throws ParcoursException {
        Familles familleSaved = famillesService.posterFamille(Familles.builder()
                .libelle("Famille " + codeDistributeur)
                .description("Famille de test " + codeDistributeur)
                .etat("true")
                .dateCreation(new Date())
                .build());

        Distributeurs distributeurSaved = creerDistributeur(codeDistributeur, mailDistributeur);

        Ressources ressourceSaved = ressourceService.posterRessources(Ressources.builder()
                .libelle("Ressource " + codeDistributeur)
                .description("Ressource de test " + codeDistributeur)
                .etat(true)
                .quantite(50)
                .seuilAlerte(5)
                .prixEntree(1000.0)
                .prixSortie(1300.0)
                .unite("PCS")
                .famille(familleSaved)
                .dateCreation(new Date())
                .build());

        return precoMouvementsService.posterPrecoMouvements(PrecoMouvements.builder()
                .libelle("Preco " + codeDistributeur)
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(PrecoMouvementsQtes.builder()
                        .quantiteMin(3)
                        .quantiteMax(9)
                        .montantMin(300.0)
                        .montantMax(900.0)
                        .ressource(ressourceSaved)
                        .familles(List.of(familleSaved))
                        .distributeurs(List.of(distributeurSaved))
                        .dateCreation(new Date())
                        .build()))
                .build());
    }

    private Distributeurs creerDistributeur(String code, String mail) throws ParcoursException {
        return distributeurService.posterDistributeur(Distributeurs.builder()
                .mail(mail)
                .telephone("0123456789")
                .adresse("Adresse de test")
                .code(code)
                .raisonSociale("Distributeur " + code)
                .dateCreation(new Date())
                .precoMouvementsQtes(new ArrayList<>())
                .promotions(new ArrayList<>())
                .build());
    }
}
