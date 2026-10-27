package cmr.notep;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Exemplaires;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.CaissesReelles;
import cmr.notep.modele.Correspondants;
import cmr.notep.modele.ExemplaireAttributs;
import cmr.notep.modele.Mouvements;
import cmr.notep.modele.MouvementSoldeCaisses;
import cmr.notep.modele.RessourcesReelles;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExemplaireCrudTest extends AbstractIttest {

 List<Exemplaires> exemplairesList = null;
 @Test
@SneakyThrows
  @Order(1)
 public void testAvoirTousExemplaires() throws ParcoursException {

    exemplairesList = exemplaireService.avoirTousExemplaires();
    exemplairesList.sort(Comparator.comparing(Exemplaires::getId));

    Assertions.assertEquals(6, exemplairesList.size(), "Le jeu de données exemplaire doit contenir 6 exemplaires.");

    Exemplaires exemplaireAvecMouvements = exemplairesList.stream()
            .filter(exemplaire -> "3190615e-1101-7209-9932-7020bbd556f7".equals(exemplaire.getId()))
            .findFirst()
            .orElseThrow();

    Assertions.assertNotNull(exemplaireAvecMouvements.getPersonneEmettrice(), "La personne émettrice doit être remontée par le service métier.");
    Assertions.assertNotNull(exemplaireAvecMouvements.getPersonneEmettrice(), "La personne émettrice doit être enrichie via le gateway document.");
    Assertions.assertEquals("EMAIL", exemplaireAvecMouvements.getPersonneEmettrice().getFirst().getMethodeUtilisee());
    Assertions.assertEquals(2, exemplaireAvecMouvements.getMouvements().size(), "Cet exemplaire de référence doit exposer ses mouvements synchronisés.");

    var mouvement = exemplaireAvecMouvements.getMouvements().stream()
            .filter(item -> "97d314f5-84ad-47cd-874d-9c55f0e45790".equals(item.getId()))
            .findFirst()
            .orElseThrow();

    Assertions.assertEquals(10, mouvement.getQuantiteMouvementee());
    Assertions.assertEquals(0, mouvement.getQuantiteInitiale());
    Assertions.assertEquals(10, mouvement.getQuantiteFinale());
    Assertions.assertEquals("ENTREE", mouvement.getTypeTransaction());
    Assertions.assertEquals("VALIDEE", mouvement.getStatut());
    Assertions.assertNotNull(mouvement.getRessourceReelle(), "La ressource réelle doit être remontée dans les services métier.");
    Assertions.assertNotNull(mouvement.getRessourceReelle().getRessource(), "La ressource documentaire enrichie doit rester disponible.");
    Assertions.assertNotNull(mouvement.getRessourceReelle().getRessource().getId(), "La ressource liée doit avoir un identifiant.");

    Assertions.assertNotNull(exemplaireAvecMouvements.getMouvementSoldeCaisses(), "Les mouvements de caisse doivent être exposés par le service métier.");
    Assertions.assertTrue(exemplaireAvecMouvements.getMouvementSoldeCaisses().isEmpty(), "Le jeu de données de référence ne contient pas encore de mouvement de caisse.");

    Assertions.assertFalse(exemplaireAvecMouvements.getPersonnesDestinataires().isEmpty(), "Les destinataires doivent rester remontés.");
    Assertions.assertNotNull(exemplaireAvecMouvements.getPersonnesDestinataires().getFirst().getDateEchange(), "La date d'échange canonique doit être valorisée.");
    Assertions.assertNotNull(exemplaireAvecMouvements.getPersonnesDestinataires().getFirst().getMethodeUtilisee(), "La méthode d'envoi doit rester synchronisée.");
 }

  @Test
  @SneakyThrows
  @Order(2)
  public void testPosterExemplaire() throws ParcoursException {
    java.util.Date now = new java.util.Date();

    Attributs attribut = attributService.avoirAttribut("a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
    Attributs attribut1 = attributService.avoirAttribut("a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12");

    var personneBeneficiaire = personneService.avoirPersonne("1979bd79-f71b-498b-b247-e7b9bbb3f600");
    var personneRattachee = personneService.avoirPersonne("d301ff83-2a62-4e6d-aa23-57c7825bcd57");
    var personneEmettrice = personneService.avoirPersonne("fdbff692-6b8d-43ab-a196-fcc3114b2daa");
    var exemplaireReference = exemplaireService.avoirExemplaire("3190615e-1101-7209-9932-7020bbd556f7");
    var ressourceReelleReference = exemplaireReference.getMouvements().stream()
            .map(Mouvements::getRessourceReelle)
            .filter(java.util.Objects::nonNull)
            .findFirst()
            .orElseThrow();

    RessourcesReelles ressourceMvt1 = RessourcesReelles.builder()
            .id(ressourceReelleReference.getId())
            .version(ressourceReelleReference.getVersion())
            .quantite(ressourceReelleReference.getQuantite())
            .seuilAlerte(ressourceReelleReference.getSeuilAlerte())
            .prixEntree(ressourceReelleReference.getPrixEntree())
            .prixSortie(ressourceReelleReference.getPrixSortie())
            .unite(ressourceReelleReference.getUnite())
            .scanBarCode(ressourceReelleReference.getScanBarCode())
            .ressource(ressourceReelleReference.getRessource())
            .build();

    RessourcesReelles ressourceMvt2 = RessourcesReelles.builder()
            .id(ressourceReelleReference.getId())
            .version(ressourceReelleReference.getVersion())
            .quantite(ressourceReelleReference.getQuantite())
            .seuilAlerte(ressourceReelleReference.getSeuilAlerte())
            .prixEntree(ressourceReelleReference.getPrixEntree())
            .prixSortie(ressourceReelleReference.getPrixSortie())
            .unite(ressourceReelleReference.getUnite())
            .scanBarCode(ressourceReelleReference.getScanBarCode())
            .ressource(ressourceReelleReference.getRessource())
            .build();

    Mouvements mouvementRessource1 = Mouvements.builder()
            .description("Mouvement ressource 1")
            .quantiteInitiale(10)
            .quantiteMouvementee(2)
            .quantiteFinale(12)
            .prix(500d)
            .typeTransaction("ENTREE")
            .validee(false)
            .statut("EN_ATTENTE")
            .dateCreation(now)
            .dateModification(now)
            .ressourceReelle(ressourceMvt1)
            .build();

    Mouvements mouvementRessource2 = Mouvements.builder()
            .description("Mouvement ressource 2")
            .quantiteInitiale(12)
            .quantiteMouvementee(1)
            .quantiteFinale(11)
            .prix(500d)
            .typeTransaction("SORTIE")
            .validee(false)
            .statut("EN_ATTENTE")
            .dateCreation(now)
            .dateModification(now)
            .ressourceReelle(ressourceMvt2)
            .build();

    MouvementSoldeCaisses mouvementCaisse1 = MouvementSoldeCaisses.builder()
            .caissesReelles(CaissesReelles.builder()
                    .id("8190615e-1101-7209-9932-7020bbd556f1")
                    .solde(200000d)
                    .build())
            .soldeInitiale(200000d)
            .soldeMovementee(5000d)
            .soldeFinale(205000d)
            .typeTransaction("CREDIT")
            .description("Mouvement caisse 1")
            .motif("Encaissement")
            .codeReference("CAISSE-MVT-001")
            .createdBy("ittest")
            .dateCreation(now)
            .validee(false)
            .statut("EN_ATTENTE")
            .build();

    MouvementSoldeCaisses mouvementCaisse2 = MouvementSoldeCaisses.builder()
            .caissesReelles(CaissesReelles.builder()
                    .id("8190615e-1101-7209-9932-7020bbd556f2")
                    .solde(205000d)
                    .build())
            .soldeInitiale(205000d)
            .soldeMovementee(2500d)
            .soldeFinale(202500d)
            .typeTransaction("DEBIT")
            .description("Mouvement caisse 2")
            .motif("Décaissement")
            .codeReference("CAISSE-MVT-002")
            .createdBy("ittest")
            .dateCreation(now)
            .validee(false)
            .statut("EN_ATTENTE")
            .build();

    Exemplaires exemplaire = new Exemplaires();
    exemplaire.setIdDocument("0190615e-1101-7209-9932-7020bbd556f1");
    exemplaire.setCode("EX-TST-001");
    exemplaire.setCodeBarre("BAR-TST-001");
    exemplaire.setIntitule("Exemplaire de test complet");
    exemplaire.setDateCreation(now);
    exemplaire.setDateModification(now);
    exemplaire.setIdExemplairesParents(List.of(
            "239d9d41-4850-4984-9d81-189cf44d867d",
            "14d6a6a5-e470-4a43-a33b-37c294974461"
    ));
    exemplaire.setPersonneBeneficiaire(personneBeneficiaire);
    exemplaire.setPersonneRattachee(personneRattachee);
    exemplaire.setPersonneEmettrice(List.of(Correspondants.builder()
            .personne(personneEmettrice)
            .dateEchange(now)
            .methodeUtilisee("EMAIL")
            .build()));
    exemplaire.setPersonnesDestinataires(List.of(Correspondants.builder()
            .personne(personneBeneficiaire)
            .dateEchange(now)
            .methodeUtilisee("SMS")
            .build()));
    exemplaire.setExemplaireAttributs(List.of(
            ExemplaireAttributs.builder()
                    .attribut(attribut)
                    .valeur("179")
                    .dateCreation(now)
                    .dateModification(now)
                    .build(),
            ExemplaireAttributs.builder()
                    .attribut(attribut1)
                    .valeur("82")
                    .dateCreation(now)
                    .dateModification(now)
                    .build()
    ));
    exemplaire.setMouvements(List.of(mouvementRessource1, mouvementRessource2));
    exemplaire.setMouvementSoldeCaisses(List.of(mouvementCaisse1, mouvementCaisse2));

    Assertions.assertNull(mouvementRessource1.getId(), "Le premier mouvement ressource ne doit pas avoir d'identifiant avant POST.");
    Assertions.assertNull(mouvementRessource2.getId(), "Le second mouvement ressource ne doit pas avoir d'identifiant avant POST.");
    Assertions.assertNull(mouvementCaisse1.getId(), "Le premier mouvement caisse ne doit pas avoir d'identifiant avant POST.");
    Assertions.assertNull(mouvementCaisse2.getId(), "Le second mouvement caisse ne doit pas avoir d'identifiant avant POST.");

    Exemplaires exemplaireEnregistre = exemplaireService.posterExemplaire(exemplaire);

    Assertions.assertNotNull(exemplaireEnregistre.getId(), "L'exemplaire doit recevoir un identifiant après enregistrement.");
    Assertions.assertEquals(exemplaire.getCode(), exemplaireEnregistre.getCode());
    Assertions.assertEquals(exemplaire.getCodeBarre(), exemplaireEnregistre.getCodeBarre());
    Assertions.assertEquals(2, exemplaireEnregistre.getIdExemplairesParents().size(), "Les parents doivent être conservés.");
    Assertions.assertEquals(1, exemplaireEnregistre.getPersonneEmettrice().size(), "La personne émettrice doit être persistée en liste.");
    Assertions.assertEquals(1, exemplaireEnregistre.getPersonnesDestinataires().size(), "La personne destinataire doit être persistée en liste.");
    Assertions.assertEquals(2, exemplaireEnregistre.getExemplaireAttributs().size(), "Les attributs de l'exemplaire doivent être enregistrés.");
    Assertions.assertEquals(2, exemplaireEnregistre.getMouvements().size(), "Deux mouvements ressources doivent être persistés.");
    Assertions.assertEquals(2, exemplaireEnregistre.getMouvementSoldeCaisses().size(), "Deux mouvements caisse doivent être persistés.");

    Exemplaires exemplaireRelu = exemplaireService.avoirExemplaire(exemplaireEnregistre.getId());
    Assertions.assertNotNull(exemplaireRelu);
    Assertions.assertEquals(exemplaireEnregistre.getId(), exemplaireRelu.getId());
    Assertions.assertEquals(exemplaire.getCode(), exemplaireRelu.getCode());
    Assertions.assertEquals(2, exemplaireRelu.getExemplaireAttributs().size());
    Assertions.assertEquals(2, exemplaireRelu.getMouvements().size(), "Les deux mouvements ressources doivent être relus.");
    Assertions.assertEquals(2, exemplaireRelu.getMouvementSoldeCaisses().size(), "Les deux mouvements caisse doivent être relus.");
    Assertions.assertEquals("179", exemplaireRelu.getExemplaireAttributs().stream()
            .filter(item -> item.getAttribut() != null && attribut.getId().equals(item.getAttribut().getId()))
            .findFirst()
            .orElseThrow()
            .getValeur());
    Assertions.assertEquals("82", exemplaireRelu.getExemplaireAttributs().stream()
            .filter(item -> item.getAttribut() != null && attribut1.getId().equals(item.getAttribut().getId()))
            .findFirst()
            .orElseThrow()
            .getValeur());

    exemplairesList = exemplaireService.avoirTousExemplaires();
    Assertions.assertEquals(7, exemplairesList.size(), "La création doit ajouter un exemplaire au jeu de données.");
    Assertions.assertTrue(exemplairesList.stream().anyMatch(item -> exemplaireEnregistre.getId().equals(item.getId())));
  }

}
