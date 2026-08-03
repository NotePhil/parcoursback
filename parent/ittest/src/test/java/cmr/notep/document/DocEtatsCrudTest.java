package cmr.notep.document;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.DocEtats;
import cmr.notep.modele.Etapes;
import cmr.notep.modele.Etats;
import cmr.notep.modele.Validations;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests d'intégration pour DocEtats :
 * - modification de la liste predecesseurDocEtat
 * - modification de l'étape, de l'état et de la validation
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class DocEtatsCrudTest extends AbstractIttest {

    // IDs issus des données de test (data-h2.sql)
    private static final String ID_DOC_ETAT_A_MODIFIER   = "be8ef4af-d5a2-4254-cd79-af860672553e";
    private static final String ID_NOUVEAU_PREDECESSEUR   = "be8ef4af-d5a2-4254-cd79-af860672554e";
    private static final String ID_ANCIEN_PREDECESSEUR    = "be8ef4af-d5a2-4254-cd79-af860672555e";

    private static final String ID_NOUVEL_ETAT       = "e190615e-1101-7209-9932-7020bbd556f2"; // Valide
    private static final String ID_NOUVELLE_ETAPE    = "1901bd79-f71b-498b-b247-e7b9bbb3f601"; // Cartouche d'encre
    private static final String ID_NOUVELLE_VALIDATION = "1901bd80-f71b-498b-b247-e7b9bbb3f603"; // Consultation

    @Test
    @SneakyThrows
    @Order(1)
    public void testModifierDocEtat() {

        // -----------------------------------------------------------------------
        // 1. Charger le DocEtat existant
        // -----------------------------------------------------------------------
        DocEtats docEtat = docEtatService.avoirDocEtat(ID_DOC_ETAT_A_MODIFIER);
        Assertions.assertNotNull(docEtat, "Le DocEtat doit exister en base");
        log.info("DocEtat initial : {}", docEtat);

        // -----------------------------------------------------------------------
        // 2. Modifier la liste predecesseurDocEtat
        //    → retirer l'ancien prédécesseur, ajouter le nouveau
        // -----------------------------------------------------------------------
        List<DocEtats> nouveauxPredecesseurs = new ArrayList<>();
        DocEtats nouveauPredecesseur = docEtatService.avoirDocEtat(ID_NOUVEAU_PREDECESSEUR);
        Assertions.assertNotNull(nouveauPredecesseur, "Le nouveau prédécesseur doit exister en base");
        nouveauxPredecesseurs.add(nouveauPredecesseur);
        docEtat.setPredecesseurDocEtat(nouveauxPredecesseurs);

        // -----------------------------------------------------------------------
        // 3. Modifier l'état
        // -----------------------------------------------------------------------
        Etats nouvelEtat = etatsService.avoirEtat(ID_NOUVEL_ETAT);
        Assertions.assertNotNull(nouvelEtat, "Le nouvel état doit exister en base");
        docEtat.setEtat(nouvelEtat);

        // -----------------------------------------------------------------------
        // 4. Modifier l'étape  (pas d'API dédiée → référence par ID)
        // -----------------------------------------------------------------------
       // Etapes nouvelleEtape = Etapes.builder().id(ID_NOUVELLE_ETAPE).build();
        //docEtat.setEtape(nouvelleEtape);

        // -----------------------------------------------------------------------
        // 5. Modifier la validation
        // -----------------------------------------------------------------------
        Validations nouvelleValidation = validationsService.avoirValidation(ID_NOUVELLE_VALIDATION);
        Assertions.assertNotNull(nouvelleValidation, "La nouvelle validation doit exister en base");
        docEtat.setValidation(nouvelleValidation);

        // -----------------------------------------------------------------------
        // 6. Sauvegarder via posterDocEtat
        // -----------------------------------------------------------------------
        DocEtats docEtatSauvegarde = docEtatService.posterDocEtat(docEtat);
        Assertions.assertNotNull(docEtatSauvegarde, "Le DocEtat sauvegardé ne doit pas être null");
        Assertions.assertEquals(ID_DOC_ETAT_A_MODIFIER, docEtatSauvegarde.getId(),
                "L'identifiant du DocEtat ne doit pas changer");

        // -----------------------------------------------------------------------
        // 7. Recharger depuis la base et vérifier toutes les modifications
        // -----------------------------------------------------------------------
        DocEtats docEtatRecharge = docEtatService.avoirDocEtat(ID_DOC_ETAT_A_MODIFIER);
        log.info("DocEtat rechargé : {}", docEtatRecharge);

        // Vérification de l'état
        Assertions.assertNotNull(docEtatRecharge.getEtat(), "L'état ne doit pas être null");
        Assertions.assertEquals(ID_NOUVEL_ETAT, docEtatRecharge.getEtat().getId(),
                "L'état doit avoir été mis à jour");

        // Vérification de l'étape
        //Assertions.assertNotNull(docEtatRecharge.getEtape(), "L'étape ne doit pas être null");
        //Assertions.assertEquals(ID_NOUVELLE_ETAPE, docEtatRecharge.getEtape().getId(),
        //        "L'étape doit avoir été mise à jour");

        // Vérification de la validation
        Assertions.assertNotNull(docEtatRecharge.getValidation(), "La validation ne doit pas être null");
        Assertions.assertEquals(ID_NOUVELLE_VALIDATION, docEtatRecharge.getValidation().getId(),
                "La validation doit avoir été mise à jour");

        // Vérification de la liste predecesseurDocEtat
        List<DocEtats> predecesseurs = docEtatRecharge.getPredecesseurDocEtat();
        Assertions.assertNotNull(predecesseurs, "La liste des prédécesseurs ne doit pas être null");
        Assertions.assertEquals(1, predecesseurs.size(),
                "La liste des prédécesseurs doit contenir exactement un élément");
        Assertions.assertEquals(ID_NOUVEAU_PREDECESSEUR, predecesseurs.getFirst().getId(),
                "Le nouveau prédécesseur doit être présent");

        boolean ancienPredecesseurAbsent = predecesseurs.stream()
                .noneMatch(p -> ID_ANCIEN_PREDECESSEUR.equals(p.getId()));
        Assertions.assertTrue(ancienPredecesseurAbsent,
                "L'ancien prédécesseur doit avoir été retiré de la liste");
    }
}



