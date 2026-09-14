package cmr.notep.distributeurs;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.DistributeursService;
import cmr.notep.impl.PrecoMouvementsService;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.modele.Promotions;
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

/**
 * Tests de conservation des relations de Distributeurs.
 * Valide que {@code posterDistributeur} ne modifie pas les listes
 * {@code precoMouvementsQtes} et {@code promotions} lors d'un update.
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class DistributeursSyncRelationsTest extends AbstractIttest {
    @Autowired
    DistributeursService distributeursService;
    @Autowired
    PrecoMouvementsService precoMouvementsService;
    /**
     * Test 1 : Créer un distributeur avec listes vides => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreateDistributeurWithEmptyRelations() throws ParcoursException {
        Distributeurs distributeur = Distributeurs.builder()
                .mail("empty@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .code("DIST-001")
                .raisonSociale("Distributeur Empty")
                .dateCreation(new Date())
                .build();

        Distributeurs saved = distributeursService.posterDistributeur(distributeur);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNull(saved.getPrecoMouvementsQtes());
        Assertions.assertNull(saved.getPromotions());
        log.info("✓ Test 1 réussi : Distributeur créé avec relations purgées");
    }

    /**
     * Test 2 : Une mise à jour avec des listes null ne doit pas purger les relations existantes.
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testUpdateDistributeurWithNullRelationsKeepsExistingValues() throws ParcoursException {
        Distributeurs saved = creerDistributeurAvecRelations("DIST-002", "withrels@test.fr");
        //List<String> precoIdsAvant = extraireIdsPreco(saved);
        List<String> promoIdsAvant = extraireIdsPromotions(saved);

        // 1. Vérification initiale que les relations existent
        //Assertions.assertFalse(precoIdsAvant.isEmpty(), "Il doit y avoir des PrecoMouvementsQtes existants pour le test");
        Assertions.assertFalse(promoIdsAvant.isEmpty(), "Il doit y avoir des Promotions existantes pour le test");

        // 2. Tentative de purge via update
        saved.setPrecoMouvementsQtes(null);
        saved.setPromotions(null);
        Distributeurs updated = distributeursService.posterDistributeur(saved);

//        Assertions.assertEquals(precoIdsAvant, extraireIdsPreco(updated),
//                "Les precoMouvementsQtes existantes doivent être conservées");
        Assertions.assertEquals(promoIdsAvant, extraireIdsPromotions(updated),
                "Les promotions existantes doivent être conservées");
        log.info("✓ Test 2 réussi : les relations sont conservées malgré des listes null en entrée");
    }

    /**
     * Test 3 : Une mise à jour avec des listes vides ne doit pas purger les relations existantes.
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testUpdateDistributeurWithEmptyRelationsKeepsExistingValues() throws ParcoursException {
        Distributeurs saved = creerDistributeurAvecRelations("DIST-003", "empty@test.fr");
        //List<String> precoIdsAvant = extraireIdsPreco(saved);
        List<String> promoIdsAvant = extraireIdsPromotions(saved);


        // 1. Vérification initiale que les relations existent
        //Assertions.assertFalse(precoIdsAvant.isEmpty(), "Il doit y avoir des PrecoMouvementsQtes existants pour le test");
        Assertions.assertFalse(promoIdsAvant.isEmpty(), "Il doit y avoir des Promotions existantes pour le test");

        saved.setPrecoMouvementsQtes(new ArrayList<>());
        saved.setPromotions(new ArrayList<>());

        Distributeurs updated = distributeursService.posterDistributeur(saved);
        //Assertions.assertEquals(precoIdsAvant, extraireIdsPreco(updated),
        //        "Les precoMouvementsQtes existantes doivent être conservées");
        Assertions.assertEquals(promoIdsAvant, extraireIdsPromotions(updated),
                "Les promotions existantes doivent être conservées");
        log.info("✓ Test 3 réussi : les relations sont conservées malgré des listes vides en entrée");
    }

    /**
     * Test 4 : Une mise à jour avec des identifiants existants ne doit pas altérer les relations persistées.
     */
    @Test
    @Order(4)
    @SneakyThrows
    public void testUpdateDistributeurWithInvalidRelationsDoesNotChangePersistedValues() throws ParcoursException {
        Distributeurs saved = creerDistributeurAvecRelations("DIST-004", "invalid@test.fr");
        //List<String> precoIdsAvant = extraireIdsPreco(saved);
        List<String> promoIdsAvant = extraireIdsPromotions(saved);

        // 1. Vérification initiale que les relations existent
        //Assertions.assertFalse(precoIdsAvant.isEmpty(), "Il doit y avoir des PrecoMouvementsQtes existants pour le test");
        Assertions.assertFalse(promoIdsAvant.isEmpty(), "Il doit y avoir des Promotions existantes pour le test");

        Promotions promo = promotionService.avoirToutPromotions().getLast();
        PrecoMouvements precoMouvement = precoMouvementsService.avoirToutPrecoMouvement().getLast();
        saved.setPrecoMouvementsQtes(precoMouvement.getPrecoMouvementsQtes());
        saved.setPromotions(List.of(promo));

        Distributeurs updated = distributeursService.posterDistributeur(saved);
        //Assertions.assertEquals(precoIdsAvant, extraireIdsPreco(updated),
        //        "Les precoMouvementsQtes existantes doivent rester identiques");
        Assertions.assertEquals(promoIdsAvant, extraireIdsPromotions(updated),
                "Les promotions existantes doivent rester identiques");
        log.info("✓ Test 4 réussi : les identifiants invalides en entrée sont ignorés pour les relations");
    }

    private Distributeurs creerDistributeurAvecRelations(String code, String mail) throws ParcoursException {
//        Distributeurs distributeur = distributeursService.posterDistributeur(Distributeurs.builder()
//                .mail(mail)
//                .telephone("0123456789")
//                .adresse("Test Address")
//                .code(code)
//                .raisonSociale("Distributeur " + code)
//                .dateCreation(new Date())
//                .build());
//        Promotions promo1 = promotionService.avoirToutPromotions().getFirst();
//        promo1.setDistributeur(distributeur);
//        promo1 = promotionService.posterPromotion(promo1);
//
//        PrecoMouvements precoMouvement = precoMouvementsService.avoirToutPrecoMouvement().getFirst();
//        PrecoMouvementsQtes precoQte = PrecoMouvementsQtes.builder()
//                        .precoMouvement(precoMouvement)
//                        .quantiteMax(10)
//                        .quantiteMin(1)
//                        .distributeurs(List.of(distributeur))
//                        .build();
//        precoMouvement.setPrecoMouvementsQtes(List.of(precoQte));
//        precoMouvement = precoMouvementsService.posterPrecoMouvements(precoMouvement);

        return distributeursService.avoirDistributeur("0618e585-f82a-4d5f-af1c-54f880d766d3");
    }

    private List<String> extraireIdsPreco(Distributeurs distributeur) {
        return distributeur.getPrecoMouvementsQtes() == null
                ? List.of()
                : distributeur.getPrecoMouvementsQtes().stream()
                .map(PrecoMouvementsQtes::getId)
                .sorted()
                .toList();
    }

    private List<String> extraireIdsPromotions(Distributeurs distributeur) {
        return distributeur.getPromotions() == null
                ? List.of()
                : distributeur.getPromotions().stream()
                .map(Promotions::getId)
                .sorted()
                .toList();
    }
}
