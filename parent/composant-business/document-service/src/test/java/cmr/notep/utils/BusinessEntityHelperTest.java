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

