package cmr.notep.promotions;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.Familles;
import cmr.notep.modele.Promotions;
import cmr.notep.modele.Ressources;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class PromotionsPosterTest extends AbstractIttest {

    private static final String DISTRIBUTEUR_ID = "1979bd79-f71b-498b-b247-e7b9bbb3f600";
    private static final String DISTRIBUTEUR_2_ID = "0618e585-f82a-4d5f-af1c-54f880d766d3";
    private static final String PROMO_001_ID = "7190615e-1101-7209-9932-7020bbd556f1";
    private static final String RESSOURCE_1_ID = "6190615e-1101-7209-9932-7020bbd556f1";
    private static final String RESSOURCE_2_ID = "6190615e-1101-7209-9932-7020bbd556f2";
    private static final String RESSOURCE_3_ID = "6190615e-1101-7209-9932-7020bbd556f3";
    private static final String FAMILLE_1_ID = "f190615e-1101-7209-9932-7020bbd556f1";
    private static final String FAMILLE_2_ID = "f190615e-1101-7209-9932-7020bbd556f2";
    private static final String FAMILLE_3_ID = "f190615e-1101-7209-9932-7020bbd556f3";

    @Test
    @Order(1)
    @SneakyThrows
    public void testPosterPromotionAvecRechargement() {
        Distributeurs distributeur = distributeurService.avoirDistributeur(DISTRIBUTEUR_ID);
        Ressources ressource1 = ressourceService.avoirRessource(RESSOURCE_1_ID);
        Ressources ressource2 = ressourceService.avoirRessource(RESSOURCE_2_ID);
        Familles famille1 = familleService.avoirFamille(FAMILLE_1_ID);
        Familles famille2 = familleService.avoirFamille(FAMILLE_2_ID);

        Assertions.assertNotNull(distributeur, "Le distributeur de référence doit exister");
        Assertions.assertNotNull(ressource1, "La ressource 1 de référence doit exister");
        Assertions.assertNotNull(ressource2, "La ressource 2 de référence doit exister");

        Promotions nouvellePromotion = Promotions.builder()
                .dateDebut(new Date())
                .dateFin(new Date())
                .codeUnique("PROMO-POST-RELOAD-001")
                .typeRemise("Pourcentage")
                .pourcentageRemise(15.0)
                .dateCreation(new Date())
                .distributeur(distributeur)
                .ressources(List.of(
                        ressource1,
                        ressource2
                ))
                .familles(List.of(
                        famille1,
                        famille2
                ))
                .build();

        Promotions saved = promotionService.posterPromotion(nouvellePromotion);
        Assertions.assertNotNull(saved.getId(), "La promotion sauvegardée doit avoir un identifiant");

        Promotions reloaded = promotionService.avoirPromotion(saved.getId());
        Assertions.assertNotNull(reloaded, "La promotion doit pouvoir être relue après sauvegarde");
        Assertions.assertEquals("PROMO-POST-RELOAD-001", reloaded.getCodeUnique());
        Assertions.assertNotNull(reloaded.getDistributeur(), "Le distributeur doit être présent après recharge");
        Assertions.assertEquals(DISTRIBUTEUR_ID, reloaded.getDistributeur().getId(),
                "Le distributeur relu doit correspondre au distributeur existant choisi");

        Assertions.assertNotNull(reloaded.getRessources(), "Les ressources doivent être présentes après recharge");
        Assertions.assertEquals(2, reloaded.getRessources().size(), "La promotion doit contenir 2 ressources après recharge");
        Assertions.assertTrue(reloaded.getRessources().stream().anyMatch(r -> RESSOURCE_1_ID.equals(r.getId())),
                "La ressource 1 doit être liée à la promotion relue");
        Assertions.assertTrue(reloaded.getRessources().stream().anyMatch(r -> RESSOURCE_2_ID.equals(r.getId())),
                "La ressource 2 doit être liée à la promotion relue");

        Assertions.assertNotNull(reloaded.getFamilles(), "Les familles doivent être présentes après recharge");
        Assertions.assertEquals(2, reloaded.getFamilles().size(), "La promotion doit contenir 2 familles après recharge");
        Assertions.assertTrue(reloaded.getFamilles().stream().anyMatch(f -> FAMILLE_1_ID.equals(f.getId())),
                "La famille 1 doit être liée à la promotion relue");
        Assertions.assertTrue(reloaded.getFamilles().stream().anyMatch(f -> FAMILLE_2_ID.equals(f.getId())),
                "La famille 2 doit être liée à la promotion relue");

        log.info("✓ Test promotion réussi : création et rechargement avec relations persistées");
    }

    @Test
    @Order(2)
    @SneakyThrows
    public void testModifierPromotionExistante() {
        // --- Vérification de l'état initial de PROMO-001 ---
        Promotions promoOriginale = promotionService.avoirPromotion(PROMO_001_ID);
        Assertions.assertNotNull(promoOriginale, "PROMO-001 doit exister dans le JDD");
        Assertions.assertEquals(DISTRIBUTEUR_ID, promoOriginale.getDistributeur().getId(),
                "PROMO-001 doit initialement référencer le distributeur ENEO");
        Assertions.assertEquals(2, promoOriginale.getRessources().size(),
                "PROMO-001 doit initialement avoir 2 ressources");
        Assertions.assertEquals(2, promoOriginale.getFamilles().size(),
                "PROMO-001 doit initialement avoir 2 familles");

        // --- Chargement des entités pour la modification ---
        Distributeurs nouveauDistributeur = distributeurService.avoirDistributeur(DISTRIBUTEUR_2_ID);
        Ressources ressource2 = ressourceService.avoirRessource(RESSOURCE_2_ID); // conservée
        Ressources ressource3 = ressourceService.avoirRessource(RESSOURCE_3_ID); // ajoutée
        Familles famille1 = familleService.avoirFamille(FAMILLE_1_ID);           // conservée
        Familles famille3 = familleService.avoirFamille(FAMILLE_3_ID);           // ajoutée

        Assertions.assertNotNull(nouveauDistributeur, "Le nouveau distributeur doit exister");
        Assertions.assertNotNull(ressource3, "La ressource 3 (ajoutée) doit exister");
        Assertions.assertNotNull(famille3, "La famille 3 (ajoutée) doit exister");

        // --- Construction de la promotion modifiée (même ID = update) ---
        // Modifications : distributeur ENEO → Brasserie
        //                 ressources : supprimer f1 (Paracetamol), ajouter f3 (Scanner)
        //                 familles   : supprimer famille2, ajouter famille3 (BioMedical)
        Promotions promoModifiee = Promotions.builder()
                .id(PROMO_001_ID)
                .dateDebut(promoOriginale.getDateDebut())
                .dateFin(promoOriginale.getDateFin())
                .codeUnique(promoOriginale.getCodeUnique())
                .typeRemise(promoOriginale.getTypeRemise())
                .pourcentageRemise(promoOriginale.getPourcentageRemise())
                .montantRemise(promoOriginale.getMontantRemise())
                .dateCreation(promoOriginale.getDateCreation())
                .distributeur(nouveauDistributeur)
                .ressources(List.of(ressource2, ressource3))
                .familles(List.of(famille1, famille3))
                .build();

        Promotions resultat = promotionService.posterPromotion(promoModifiee);
        Assertions.assertNotNull(resultat, "La promotion modifiée doit être retournée");
        Assertions.assertEquals(PROMO_001_ID, resultat.getId(), "L'ID doit rester inchangé après modification");

        // --- Rechargement et vérifications ---
        Promotions reloaded = promotionService.avoirPromotion(PROMO_001_ID);
        Assertions.assertNotNull(reloaded, "La promotion doit toujours exister après modification");

        // Distributeur changé
        Assertions.assertNotNull(reloaded.getDistributeur(), "Le distributeur doit être présent après recharge");
        Assertions.assertEquals(DISTRIBUTEUR_2_ID, reloaded.getDistributeur().getId(),
                "Le distributeur doit être Brasserie après modification");

        // Ressources : f1 supprimée, f3 ajoutée → [f2, f3]
        Assertions.assertNotNull(reloaded.getRessources(), "Les ressources doivent être présentes après recharge");
        Assertions.assertEquals(2, reloaded.getRessources().size(),
                "La promotion doit contenir 2 ressources après modification");
        Assertions.assertFalse(reloaded.getRessources().stream().anyMatch(r -> RESSOURCE_1_ID.equals(r.getId())),
                "La ressource 1 (Paracetamol) doit avoir été dissociée");
        Assertions.assertTrue(reloaded.getRessources().stream().anyMatch(r -> RESSOURCE_2_ID.equals(r.getId())),
                "La ressource 2 (Cartouche) doit toujours être liée");
        Assertions.assertTrue(reloaded.getRessources().stream().anyMatch(r -> RESSOURCE_3_ID.equals(r.getId())),
                "La ressource 3 (Scanner) doit avoir été associée");

        // Familles : famille2 supprimée, famille3 ajoutée → [famille1, famille3]
        Assertions.assertNotNull(reloaded.getFamilles(), "Les familles doivent être présentes après recharge");
        Assertions.assertEquals(2, reloaded.getFamilles().size(),
                "La promotion doit contenir 2 familles après modification");
        Assertions.assertTrue(reloaded.getFamilles().stream().anyMatch(f -> FAMILLE_1_ID.equals(f.getId())),
                "La famille 1 (Medicaments) doit toujours être liée");
        Assertions.assertFalse(reloaded.getFamilles().stream().anyMatch(f -> FAMILLE_2_ID.equals(f.getId())),
                "La famille 2 (Consommables) doit avoir été dissociée");
        Assertions.assertTrue(reloaded.getFamilles().stream().anyMatch(f -> FAMILLE_3_ID.equals(f.getId())),
                "La famille 3 (BioMedical) doit avoir été associée");

        log.info("✓ Test promotion modifiée : distributeur, ressources et familles correctement mis à jour");
    }
}


