package cmr.notep.document;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class DocumentRelationsExtendedTest extends AbstractIttest {

    @Test
    @SneakyThrows
    @Order(1)
    public void testSyncRelationsExtended() {
        // Charger un document existant (on prend le premier document disponible)
        List<Documents> docs = documentService.avoirTousDocuments();
        Assertions.assertFalse(docs.isEmpty(), "Il doit y avoir au moins un document dans la base de test");
        Documents doc = documentService.avoirDocument("0190615e-1101-7209-9932-7020bbd556f2");

        // Précharger des références existantes en base (aucune création d'objet ici)
        List<PrecoMouvements> dbPrecos = precoMouvementService.avoirToutPrecoMouvement();
        List<Missions> dbMissions = missionService.avoirToutMissions();
        List<Promotions> dbPromotions = promotionService.avoirToutPromotions();

//        DocEtats dbDocEtat = null;
//        for (Documents currentDocRef : docs) {
//            Documents currentDoc = documentService.avoirDocument(currentDocRef.getIdDocument());
//            if (currentDoc.getDocEtats() != null && !currentDoc.getDocEtats().isEmpty()) {
//                dbDocEtat = currentDoc.getDocEtats().get(0);
//                break;
//            }
//        }

        // Préparer sauvegardes des tailles initiales
        int initialPreco = doc.getPrecoMouvements() != null ? doc.getPrecoMouvements().size() : 0;
        int initialMissions = doc.getMissions() != null ? doc.getMissions().size() : 0;
        int initialDocEtats = doc.getDocEtats() != null ? doc.getDocEtats().size() : 0;

        // Suppression d'un élément de chaque collection si présent
        PrecoMouvements removedPreco = null;
        Missions removedMission = null;
        Promotions removedPromotion = null;
        DocEtats removedDocEtat = null;
        if (doc.getPrecoMouvements() != null && !doc.getPrecoMouvements().isEmpty()) {
            removedPreco = doc.getPrecoMouvements().remove(0);
        }
        if (doc.getMissions() != null && !doc.getMissions().isEmpty()) {
            removedMission = doc.getMissions().remove(0);
        }

//        if (doc.getDocEtats() != null && !doc.getDocEtats().isEmpty()) {
//            removedDocEtat = doc.getDocEtats().remove(0);
//        }

        // Ajout d'un élément existant en base pour chaque type
        PrecoMouvements addedPreco = removedPreco != null ? removedPreco : (dbPrecos.isEmpty() ? null : dbPrecos.get(0));
        Assertions.assertNotNull(addedPreco, "Aucun PrecoMouvement existant en base pour le test");
        if (doc.getPrecoMouvements() == null) doc.setPrecoMouvements(new ArrayList<>());
        doc.getPrecoMouvements().add(addedPreco);

        Missions addedMission = removedMission != null ? removedMission : (dbMissions.isEmpty() ? null : dbMissions.get(0));
        Assertions.assertNotNull(addedMission, "Aucune Mission existante en base pour le test");
        if (doc.getMissions() == null) doc.setMissions(new ArrayList<>());
        doc.getMissions().add(addedMission);

//        DocEtats addedDocEtat = removedDocEtat != null ? removedDocEtat : dbDocEtat;
//        Assertions.assertNotNull(addedDocEtat, "Aucun DocEtat existant en base pour le test");
//        if (doc.getDocEtats() == null) doc.setDocEtats(new ArrayList<>());
//        doc.getDocEtats().add(addedDocEtat);

        // Poster le document (doit synchroniser relations transverses)
        Documents saved = documentService.posterDocument(doc);

        // Récupérer à nouveau et vérifier
        Documents reloaded = documentService.avoirDocument(saved.getIdDocument());

        // Calculer nouvelles tailles attendues
        int expectedPreco = initialPreco; // suppressed 1 then added 1 -> same as initial (unless none initially)
        if (initialPreco == 0) expectedPreco = 1; // si initialement vide on a seulement l'ajout
        int expectedMissions = initialMissions == 0 ? 1 : initialMissions; // remove+add -> same
        int expectedDocEtats = initialDocEtats == 0 ? 1 : initialDocEtats;

        int actualPreco = reloaded.getPrecoMouvements() != null ? reloaded.getPrecoMouvements().size() : 0;
        int actualMissions = reloaded.getMissions() != null ? reloaded.getMissions().size() : 0;
        int actualDocEtats = reloaded.getDocEtats() != null ? reloaded.getDocEtats().size() : 0;

        Assertions.assertEquals(saved.getIdDocument(),doc.getIdDocument(),"identifiants doivent être identiques");
        Assertions.assertEquals(expectedPreco, actualPreco, "Mismatch PrecoMouvements count");
        Assertions.assertEquals(expectedMissions, actualMissions, "Mismatch Missions count");
        Assertions.assertEquals(expectedDocEtats, actualDocEtats, "Mismatch DocEtats count");

        // Vérifier que les éléments ajoutés (issus de la base) existent après synchronisation
        boolean foundPreco = reloaded.getPrecoMouvements() != null && reloaded.getPrecoMouvements().stream()
                .anyMatch(p -> addedPreco.getId().equals(p.getId()));
        boolean foundMission = reloaded.getMissions() != null && reloaded.getMissions().stream()
                .anyMatch(m -> addedMission.getId().equals(m.getId()));
//        boolean foundDocEtat = reloaded.getDocEtats() != null && reloaded.getDocEtats().stream()
//                .anyMatch(de -> addedDocEtat.getId().equals(de.getId()));

        Assertions.assertTrue(foundPreco, "Le PrecoMouvement issu de la base doit exister");
        Assertions.assertTrue(foundMission, "La Mission issue de la base doit exister");
//        Assertions.assertTrue(foundDocEtat, "Le DocEtat issu de la base doit exister");

        // DocEtats : vérifier qu'il y a au moins un élément après synchronisation
        Assertions.assertTrue(actualDocEtats >= 1, "Il doit y avoir au moins un docEtat après synchronisation");
    }
}
