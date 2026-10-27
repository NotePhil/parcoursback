package cmr.notep.utils;

import cmr.notep.modele.DocEtats;
import cmr.notep.modele.Etats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Tests - BusinessEntityHelper")
class BusinessEntityHelperTest {

    @Test
    @DisplayName("getDocEtatsListToInsert ordonne les etats et retourne les boucles b -> (c, d)")
    void getDocEtatsListToInsertDoitOrdonnerEtRetirerBoucles() {
        DocEtats a = docEtat("a");
        DocEtats b = docEtat("b");
        DocEtats c = docEtat("c");
        DocEtats d = docEtat("d");
        DocEtats e = docEtat("e");

        // Graphe attendu en entree : a->b, b->c, b->d, d->b, c->b, b->e
        b.setPredecesseurDocEtat(new HashSet<>(Set.of(a, c, d)));
        c.setPredecesseurDocEtat(new HashSet<>(Set.of(b)));
        d.setPredecesseurDocEtat(new HashSet<>(Set.of(b)));
        e.setPredecesseurDocEtat(new HashSet<>(Set.of(b)));

        List<DocEtats> docEtats = List.of(a, b, c, d, e);
        List<DocEtats> docEtatsListToInsert = new ArrayList<>();

        Map<String, Set<DocEtats>> docEtatMapBoucle =
                BusinessEntityHelper.getDocEtatsListToInsert(docEtats, docEtatsListToInsert);

        assertEquals(
                List.of("a", "b", "c", "d", "e"),
                ids(docEtatsListToInsert),
                "L'ordre d'insertion doit etre a, b, c, d, e"
        );

        assertTrue(docEtatMapBoucle.containsKey("b"), "La map des boucles doit contenir la cle b");
        assertEquals(
                Set.of("c", "d"),
                idsAsSet(docEtatMapBoucle.get("b")),
                "Les predecesseurs cycliques de b doivent etre c et d"
        );
    }

    @Test
    @DisplayName("getDocEtatsListToInsert gere le cycle valide/rejete/en-attente")
    void getDocEtatsListToInsertDoitGererCycleComplexe() {
        DocEtats enCours = docEtat("en-cours");
        DocEtats valide = docEtat("valide");
        DocEtats rejete = docEtat("rejete");
        DocEtats enAttente = docEtat("en-attente");

        // valide precede rejete
        // en attente precede rejete
        rejete.setPredecesseurDocEtat(new HashSet<>(Set.of(valide, enAttente)));
        // en attente precede valide
        // en cours precede valide
        valide.setPredecesseurDocEtat(new HashSet<>(Set.of(enAttente, enCours)));
        // rejete precede en attente
        enAttente.setPredecesseurDocEtat(new HashSet<>(Set.of(rejete)));

        List<DocEtats> docEtats = List.of(enCours, valide, rejete, enAttente);
        List<DocEtats> docEtatsListToInsert = new ArrayList<>();

        Map<String, Set<DocEtats>> docEtatMapBoucle =
                BusinessEntityHelper.getDocEtatsListToInsert(docEtats, docEtatsListToInsert);

        assertEquals(4, docEtatsListToInsert.size(), "Tous les etats doivent etre planifies pour insertion");
        assertEquals("en-cours", docEtatsListToInsert.get(0).getEtat().getId(), "Le premier etat doit etre en-cours");
        assertTrue(ids(docEtatsListToInsert).containsAll(List.of("valide", "rejete", "en-attente")));

        assertFalse(docEtatMapBoucle.isEmpty(), "Le graphe contient une boucle qui doit etre detectee");
    }

    private static DocEtats docEtat(String idEtat) {
        return DocEtats.builder()
                .etat(Etats.builder().id(idEtat).build())
                .predecesseurDocEtat(new HashSet<>())
                .build();
    }

    private static List<String> ids(List<DocEtats> docEtats) {
        return docEtats.stream().map(docEtat -> docEtat.getEtat().getId()).collect(Collectors.toList());
    }

    private static Set<String> idsAsSet(Set<DocEtats> docEtats) {
        return docEtats.stream().map(docEtat -> docEtat.getEtat().getId()).collect(Collectors.toSet());
    }
}

