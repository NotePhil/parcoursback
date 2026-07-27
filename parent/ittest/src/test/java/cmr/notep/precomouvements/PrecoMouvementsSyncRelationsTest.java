package cmr.notep.precomouvements;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.PrecoMouvementsService;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.modele.TypeMouvement;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
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
    /**
     * Test 1 : Créer un précomouvement avec liste vide => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreatePrecoMouvementWithEmptyQuantites() throws ParcoursException {
        PrecoMouvements preco = PrecoMouvements.builder()
                .id(UUID.randomUUID().toString())
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
                .id(UUID.randomUUID().toString())
                .libelle("Preco With Qtes")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .id(UUID.randomUUID().toString())
                                .qteMin(1)
                                .qteMax(10)
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
        String precoId = UUID.randomUUID().toString();
        PrecoMouvements preco = PrecoMouvements.builder()
                .id(precoId)
                .libelle("Preco Sync Qtes")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .id(UUID.randomUUID().toString())
                                .qteMin(1)
                                .qteMax(10)
                                .montantMin(100.0)
                                .montantMax(1000.0)
                                .dateCreation(new Date())
                                .build(),
                        PrecoMouvementsQtes.builder()
                                .id(UUID.randomUUID().toString())
                                .qteMin(11)
                                .qteMax(20)
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
        String qte1Id = saved.getPrecoMouvementsQtes().get(0).getId();
        saved.setPrecoMouvementsQtes(List.of(
                PrecoMouvementsQtes.builder()
                        .id(qte1Id)  // existing => update
                        .qteMin(2)
                        .qteMax(15)
                        .montantMin(150.0)
                        .montantMax(1500.0)
                        .dateCreation(new Date())
                        .build(),
                PrecoMouvementsQtes.builder()
                        .id(UUID.randomUUID().toString())  // nouveau => create
                        .qteMin(21)
                        .qteMax(30)
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
        PrecoMouvements preco = PrecoMouvements.builder()
                .id(UUID.randomUUID().toString())
                .libelle("Preco Invalid Qte")
                .etat(true)
                .typeMouvement(TypeMouvement.Ajout)
                .dateCreation(new Date())
                .precoMouvementsQtes(List.of(
                        PrecoMouvementsQtes.builder()
                                .id("invalid-qte-id")  // ID inexistant
                                .qteMin(1)
                                .qteMax(10)
                                .montantMin(100.0)
                                .montantMax(1000.0)
                                .build()
                ))
                .build();

        Assertions.assertThrows(RuntimeException.class,
                () -> precoMouvementsService.posterPrecoMouvements(preco),
                "Doit lancer une exception si la quantité n'existe pas");
        log.info("✓ Test 4 réussi : Exception levée pour quantité inexistante");
    }
}
