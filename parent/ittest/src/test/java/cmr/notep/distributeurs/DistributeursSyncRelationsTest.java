package cmr.notep.distributeurs;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.DistributeursService;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.modele.Promotions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Tests de synchronisation des relations de Distributeurs (precoMouvementsQtes et promotions).
 * Valide les règles métier :
 * - liste absente => purge complète des relations
 * - création/update des relations
 * - suppression des relations non présentes
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class DistributeursSyncRelationsTest extends AbstractIttest {
    @Autowired
    DistributeursService distributeursService;

    /**
     * Test 1 : Créer un distributeur avec listes vides => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreateDistributeurWithEmptyRelations() throws ParcoursException {
        Distributeurs distributeur = Distributeurs.builder()
                .id(UUID.randomUUID().toString())
                .mail("empty@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .code("DIST-001")
                .raisonSociale("Distributeur Empty")
                .dateCreation(new Date())
                .precoMouvementsQtes(new ArrayList<>())  // liste vide => purge
                .promotions(new ArrayList<>())  // liste vide => purge
                .build();

        Distributeurs saved = distributeursService.posterDistributeur(distributeur);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getPrecoMouvementsQtes());
        Assertions.assertTrue(saved.getPrecoMouvementsQtes().isEmpty(),
                "PrecoMouvementsQtes doivent être vides");
        Assertions.assertNotNull(saved.getPromotions());
        Assertions.assertTrue(saved.getPromotions().isEmpty(),
                "Promotions doivent être vides");
        log.info("✓ Test 1 réussi : Distributeur créé avec relations purgées");
    }

    /**
     * Test 2 : Créer avec relations, puis purger via update
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testUpdateDistributeurWithRelationPurge() throws ParcoursException {
        // 1. Créer avec relations
        Distributeurs distributeur = Distributeurs.builder()
                .id(UUID.randomUUID().toString())
                .mail("withrels@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .code("DIST-002")
                .raisonSociale("Distributeur With Rels")
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .id(UUID.randomUUID().toString())
                                .qteMin(1)
                                .qteMax(10)
                                .build()
                ))
                .promotions(List.of(
                        Promotions.builder()
                                .id(UUID.randomUUID().toString())
                                .codeUnique("PROMO-001")
                                .dateDebut(new Date())
                                .dateFin(new Date())
                                .build()
                ))
                .build();

        Distributeurs saved = distributeursService.posterDistributeur(distributeur);
        Assertions.assertEquals(1, saved.getPrecoMouvementsQtes().size(),
                "Doit avoir 1 precoMouvementsQte");
        Assertions.assertEquals(1, saved.getPromotions().size(),
                "Doit avoir 1 promotion");

        // 2. Purger les deux listes via update
        saved.setPrecoMouvementsQtes(null);
        saved.setPromotions(null);
        Distributeurs updated = distributeursService.posterDistributeur(saved);

        Assertions.assertTrue(updated.getPrecoMouvementsQtes() == null ||
                updated.getPrecoMouvementsQtes().isEmpty(),
                "PrecoMouvementsQtes doivent être purgées");
        Assertions.assertTrue(updated.getPromotions() == null ||
                updated.getPromotions().isEmpty(),
                "Promotions doivent être purgées");
        log.info("✓ Test 2 réussi : Distributeur mis à jour avec purge des relations");
    }

    /**
     * Test 3 : Synchronisation partielle des promotions
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testUpdateDistributeurWithPromotionSync() throws ParcoursException {
        // 1. Créer avec 2 promotions
        String distributeurId = UUID.randomUUID().toString();
        Distributeurs distributeur = Distributeurs.builder()
                .id(distributeurId)
                .mail("sync@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .code("DIST-003")
                .raisonSociale("Distributeur Sync")
                .dateCreation(new Date())
                .precoMouvementsQtes(new ArrayList<>())
                .promotions(List.of(
                        Promotions.builder()
                                .id(UUID.randomUUID().toString())
                                .codeUnique("PROMO-100")
                                .dateDebut(new Date())
                                .dateFin(new Date())
                                .build(),
                        Promotions.builder()
                                .id(UUID.randomUUID().toString())
                                .codeUnique("PROMO-101")
                                .dateDebut(new Date())
                                .dateFin(new Date())
                                .build()
                ))
                .build();

        Distributeurs saved = distributeursService.posterDistributeur(distributeur);
        Assertions.assertEquals(2, saved.getPromotions().size(),
                "Doit avoir 2 promotions");

        // 2. Sync : garder 1ère, supprimer 2ème, ajouter 3ème
        String promo1Id = saved.getPromotions().get(0).getId();
        saved.setPromotions(List.of(
                Promotions.builder()
                        .id(promo1Id)  // existing => update
                        .codeUnique("PROMO-100-UPDATED")
                        .dateDebut(new Date())
                        .dateFin(new Date())
                        .build(),
                Promotions.builder()
                        .id(UUID.randomUUID().toString())  // nouveau => create
                        .codeUnique("PROMO-102")
                        .dateDebut(new Date())
                        .dateFin(new Date())
                        .build()
        ));

        Distributeurs updated = distributeursService.posterDistributeur(saved);
        Assertions.assertEquals(2, updated.getPromotions().size(),
                "Doit avoir 2 promotions après sync");
        log.info("✓ Test 3 réussi : Synchronisation partielle des promotions réussie");
    }

    /**
     * Test 4 : Erreur si promotion inexistante lors de l'update
     */
    @Test
    @Order(4)
    @SneakyThrows
    public void testUpdateDistributeurWithInvalidPromotion() {
        Distributeurs distributeur = Distributeurs.builder()
                .id(UUID.randomUUID().toString())
                .mail("invalid@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .code("DIST-004")
                .raisonSociale("Distributeur Invalid")
                .dateCreation(new Date())
                .precoMouvementsQtes(new ArrayList<>())
                .promotions(List.of(
                        Promotions.builder()
                                .id("invalid-promo-id")  // ID inexistant
                                .codeUnique("PROMO-INVALID")
                                .dateDebut(new Date())
                                .dateFin(new Date())
                                .build()
                ))
                .build();

        Assertions.assertThrows(RuntimeException.class,
                () -> distributeursService.posterDistributeur(distributeur),
                "Doit lancer une exception si la promotion n'existe pas");
        log.info("✓ Test 4 réussi : Exception levée pour promotion inexistante");
    }
}
