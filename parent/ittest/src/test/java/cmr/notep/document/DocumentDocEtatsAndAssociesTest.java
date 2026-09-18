package cmr.notep.document;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.DocEtats;
import cmr.notep.modele.Documents;
import cmr.notep.modele.DocumentsAssocies;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests d'intégration ciblés sur l'enrichissement de posterDocument :
 * - synchronisation des DocEtats (création sans id / modification avec id / purge)
 * - gestion des documentsAssocies (simple lien de dépendance vers des documents existants,
 *   aucune mise à jour du contenu des documents associés n'est autorisée)
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional
@Slf4j
public class DocumentDocEtatsAndAssociesTest extends AbstractIttest {

    private static final String ID_DOCUMENT_SANS_DOC_ETATS = "0190615e-1101-7209-9932-7020bbd556f6";
    private static final String ID_DOCUMENT_AVEC_DOC_ETAT = "0190615e-1101-7209-9932-7020bbd556f1";
    private static final String ID_DOCUMENT_ASSOCIE = "0190615e-1101-7209-9932-7020bbd556f2";
    private static final String ID_VALIDATION_EXISTANTE = "1901bd80-f71b-498b-b247-e7b9bbb3f603";
    private static final String ID_ETAT_UN = "e190615e-1101-7209-9932-7020bbd556f1";
    private static final String ID_ETAT_DEUX = "e190615e-1101-7209-9932-7020bbd556f2";
    private static final String ID_ETAT_TROIS = "e190615e-1101-7209-9932-7020bbd556f3";

    private Documents chargerDocumentExistant(String idDocument) {
        Documents document = documentService.avoirDocument(idDocument);
        Assertions.assertNotNull(document, "Le document doit exister en base");
        return document;
    }

    private Etats chargerEtatExistant(String idEtat) {
        Etats etat = etatsService.avoirEtat(idEtat);
        Assertions.assertNotNull(etat, "L'état doit exister en base");
        return etat;
    }

    private Validations chargerValidationExistante(String idValidation) {
        Validations validation = validationsService.avoirValidation(idValidation);
        Assertions.assertNotNull(validation, "La validation doit exister en base");
        return validation;
    }

    /**
     * Test 1 : création d'un DocEtat sans id via posterDocument (id absent → création)
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreationDocEtatSansIdViaPosterDocument() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);

        Etats etat = chargerEtatExistant(ID_ETAT_UN);
        Validations validation = chargerValidationExistante(ID_VALIDATION_EXISTANTE);

        DocEtats nouveauDocEtat = DocEtats.builder()
                .ordre(1)
                .etat(etat)
                .validation(validation)
                .build();

        List<DocEtats> docEtats = new ArrayList<>();
        docEtats.add(nouveauDocEtat);
        document.setDocEtats(docEtats);

        Documents saved = documentService.posterDocument(document);

        Assertions.assertNotNull(saved.getDocEtats(), "La liste des DocEtats ne doit pas être nulle");
        Assertions.assertEquals(1, saved.getDocEtats().size(), "Un DocEtat doit avoir été créé");
        DocEtats docEtatCree = saved.getDocEtats().get(0);
        Assertions.assertNotNull(docEtatCree.getId(), "Le DocEtat créé doit avoir reçu un id");
        Assertions.assertEquals(etat.getId(), docEtatCree.getEtat().getId(), "L'état doit être correctement lié");

        log.info("✓ Test 1 réussi : DocEtat créé sans id fourni, id généré = {}", docEtatCree.getId());
    }

    /**
     * Test 2 : modification d'un DocEtat existant (id présent) via posterDocument
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testModificationDocEtatAvecIdViaPosterDocument() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_AVEC_DOC_ETAT);

        Etats etatInitial = chargerEtatExistant(ID_ETAT_UN);
        Etats etatFinal = chargerEtatExistant(ID_ETAT_DEUX);
        Validations validation = chargerValidationExistante(ID_VALIDATION_EXISTANTE);

        DocEtats docEtat = DocEtats.builder()
                .ordre(1)
                .etat(etatInitial)
                .validation(validation)
                .build();
        document.setDocEtats(new ArrayList<>(List.of(docEtat)));

        Documents saved = documentService.posterDocument(document);
        String idDocEtat = saved.getDocEtats().get(0).getId();
        Assertions.assertNotNull(idDocEtat);

        // Modification : on change l'état, l'id doit être conservé
        Documents reloaded = documentService.avoirDocument(saved.getIdDocument());
        DocEtats docEtatAModifier = reloaded.getDocEtats().get(0);
        docEtatAModifier.setEtat(etatFinal);
        reloaded.setDocEtats(new ArrayList<>(List.of(docEtatAModifier)));

        Documents updated = documentService.posterDocument(reloaded);

        Assertions.assertEquals(1, updated.getDocEtats().size(), "Il doit toujours y avoir un seul DocEtat");
        DocEtats docEtatModifie = updated.getDocEtats().get(0);
        Assertions.assertEquals(idDocEtat, docEtatModifie.getId(), "L'id du DocEtat ne doit pas changer lors d'une modification");
        Assertions.assertEquals(etatFinal.getId(), docEtatModifie.getEtat().getId(), "L'état doit avoir été mis à jour");

        log.info("✓ Test 2 réussi : DocEtat modifié en conservant son id {}", idDocEtat);
    }

    /**
     * Test 3 : purge complète des DocEtats lorsque la liste fournie est vide
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testPurgeDocEtatsAvecListeVide() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);

        Etats etat = chargerEtatExistant(ID_ETAT_UN);
        Validations validation = chargerValidationExistante(ID_VALIDATION_EXISTANTE);

        DocEtats docEtat = DocEtats.builder().ordre(1).etat(etat).validation(validation).build();
        document.setDocEtats(new ArrayList<>(List.of(docEtat)));
        Documents saved = documentService.posterDocument(document);
        Assertions.assertEquals(1, saved.getDocEtats().size());

        // Purge : liste vide
        saved.setDocEtats(new ArrayList<>());
        Documents purged = documentService.posterDocument(saved);

        Assertions.assertTrue(purged.getDocEtats() == null || purged.getDocEtats().isEmpty(),
                "Les DocEtats doivent être purgés lorsque la liste fournie est vide");

        log.info("✓ Test 3 réussi : purge complète des DocEtats avec liste vide");
    }

    /**
     * Test 4 : lien de dépendance vers un document associé déjà existant, sans mise à jour de son contenu
     */
    @Test
    @Order(4)
    @SneakyThrows
    public void testLienDocumentsAssociesSansMiseAJour() {
        Documents documentPrincipal = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);
        Documents documentAssocie = chargerDocumentExistant(ID_DOCUMENT_ASSOCIE);
        Etats etatAssocie = chargerEtatExistant(ID_ETAT_UN);
        String titreOriginalAssocie = documentAssocie.getTitre();
        documentAssocie.setTitre("Titre modifié temporairement pour le test");


        Set<DocumentsAssocies> documentsAssocies = new HashSet<>();
        documentsAssocies.add(DocumentsAssocies.builder()
                .document(documentAssocie)
                .etat(etatAssocie)
                .build());
        documentPrincipal.setDocumentsAssocies(documentsAssocies);

        Documents saved = documentService.posterDocument(documentPrincipal);

        Assertions.assertNotNull(saved.getDocumentsAssocies(), "Le lien vers le document associé doit être présent");
        Assertions.assertEquals(1, saved.getDocumentsAssocies().size());
        Assertions.assertTrue(saved.getDocumentsAssocies().stream()
                        .anyMatch(d -> d.getDocument() != null
                                && d.getDocument().getIdDocument().equals(documentAssocie.getIdDocument())),
                "Le document associé doit être lié par id");

        // Vérifier que le document associé n'a pas été modifié
        Documents documentAssocieRecharge = documentService.avoirDocument(documentAssocie.getIdDocument());
        Assertions.assertEquals(titreOriginalAssocie, documentAssocieRecharge.getTitre(),
                "Le contenu du document associé ne doit jamais être mis à jour via le lien de dépendance");

        log.info("✓ Test 4 réussi : lien de dépendance tracé sans mutation du document associé");
    }

    /**
     * Test 5 : un document associé inexistant doit provoquer une exception NOT_FOUND
     */
    @Test
    @Order(5)
    @SneakyThrows
    public void testDocumentAssocieInexistantLeveNotFound() {
        Documents documentPrincipal = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);
        Etats etatAssocie = chargerEtatExistant(ID_ETAT_UN);

        DocumentsAssocies documentInexistant = DocumentsAssocies.builder()
                .document(Documents.builder().idDocument("99999999-9999-9999-9999-999999999999").build())
                .etat(etatAssocie)
                .build();
        documentPrincipal.setDocumentsAssocies(new HashSet<>(Set.of(documentInexistant)));

        ParcoursException exception = Assertions.assertThrows(ParcoursException.class,
                () -> documentService.posterDocument(documentPrincipal));
        Assertions.assertEquals(ParcoursExceptionCodeEnum.NOT_FOUND, exception.getCode());

        log.info("✓ Test 5 réussi : NOT_FOUND levée pour un document associé inexistant");
    }

    /**
     * Test 6 : un document ne peut pas être associé à lui-même
     */
    @Test
    @Order(6)
    @SneakyThrows
    public void testAutoAssociationInterdite() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);
        Etats etatAssocie = chargerEtatExistant(ID_ETAT_UN);

        DocumentsAssocies autoReference = DocumentsAssocies.builder()
                .document(Documents.builder().idDocument(document.getIdDocument()).build())
                .etat(etatAssocie)
                .build();
        document.setDocumentsAssocies(new HashSet<>(Set.of(autoReference)));

        ParcoursException exception = Assertions.assertThrows(ParcoursException.class,
                () -> documentService.posterDocument(document));
        Assertions.assertEquals(ParcoursExceptionCodeEnum.OPERATION_INTERDITE, exception.getCode());

        log.info("✓ Test 6 réussi : auto-association refusée");
    }

    /**
     * Test 7 : purge complète des documentsAssocies lorsque la liste fournie est vide
     */
    @Test
    @Order(7)
    @SneakyThrows
    public void testPurgeDocumentsAssociesAvecListeVide() {
        Documents documentPrincipal = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);
        Documents documentAssocie = chargerDocumentExistant(ID_DOCUMENT_ASSOCIE);
        Etats etatAssocie = chargerEtatExistant(ID_ETAT_UN);

        documentPrincipal.setDocumentsAssocies(new HashSet<>(Set.of(
                DocumentsAssocies.builder()
                        .document(Documents.builder().idDocument(documentAssocie.getIdDocument()).build())
                        .etat(etatAssocie)
                        .build())));
        Documents saved = documentService.posterDocument(documentPrincipal);
        Assertions.assertEquals(1, saved.getDocumentsAssocies().size());

        saved.setDocumentsAssocies(new HashSet<>());
        Documents purged = documentService.posterDocument(saved);

        Assertions.assertTrue(purged.getDocumentsAssocies() == null || purged.getDocumentsAssocies().isEmpty(),
                "Les documentsAssocies doivent être purgés lorsque la liste fournie est vide");

        log.info("✓ Test 7 réussi : purge complète des documentsAssocies avec liste vide");
    }

    /**
     * Test 8 : un document sans DocEtats doit retourner une liste vide d'états possibles
     */
    @Test
    @Order(8)
    @SneakyThrows
    public void testEtatsPossiblesDocumentSansDocEtatsRetourneListeVide() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);

        List<Etats> etatsPossibles = documentService.avoirEtatsPossiblesDocument(document.getIdDocument());

        Assertions.assertNotNull(etatsPossibles, "La réponse ne doit jamais être nulle");
        Assertions.assertTrue(etatsPossibles.isEmpty(), "La réponse doit être vide lorsqu'aucun DocEtat n'existe");

        log.info("✓ Test 8 réussi : liste vide retournée pour un document sans DocEtats");
    }

    /**
     * Test 9 : les états possibles doivent être renvoyés dans l'ordre croissant de DocEtats.ordre
     */
    @Test
    @Order(9)
    @SneakyThrows
    public void testEtatsPossiblesDocumentSontOrdonnes() {
        Documents document = chargerDocumentExistant(ID_DOCUMENT_SANS_DOC_ETATS);

        Etats etatPremier = chargerEtatExistant(ID_ETAT_UN);
        Etats etatDeuxieme = chargerEtatExistant(ID_ETAT_DEUX);
        Etats etatTroisieme = chargerEtatExistant(ID_ETAT_TROIS);

        DocEtats docEtatBas = DocEtats.builder().ordre(30).etat(etatTroisieme).build();
        DocEtats docEtatMilieu = DocEtats.builder().ordre(20).etat(etatDeuxieme).build();
        DocEtats docEtatHaut = DocEtats.builder().ordre(10).etat(etatPremier).build();

        document.setDocEtats(new ArrayList<>(List.of(docEtatBas, docEtatMilieu, docEtatHaut)));
        Documents saved = documentService.posterDocument(document);

        List<Etats> etatsPossibles = documentService.avoirEtatsPossiblesDocument(saved.getIdDocument());

        Assertions.assertEquals(3, etatsPossibles.size(), "Les trois états doivent être retrouvés");
        Assertions.assertEquals(etatPremier.getId(), etatsPossibles.get(0).getId(), "L'ordre croissant doit être respecté");
        Assertions.assertEquals(etatDeuxieme.getId(), etatsPossibles.get(1).getId(), "L'ordre croissant doit être respecté");
        Assertions.assertEquals(etatTroisieme.getId(), etatsPossibles.get(2).getId(), "L'ordre croissant doit être respecté");

        log.info("✓ Test 9 réussi : états possibles ordonnés correctement");
    }
}

