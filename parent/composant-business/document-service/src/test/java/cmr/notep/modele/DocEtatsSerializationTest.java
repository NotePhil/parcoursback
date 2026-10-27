package cmr.notep.modele;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocEtatsSerializationTest {

    @Test
    void serialiserDocEtatsCycliquesNeDoitPasDepasserLaProfondeurMax() {
        DocEtats a = DocEtats.builder().id("a").predecesseurDocEtat(new HashSet<>()).build();
        DocEtats b = DocEtats.builder().id("b").predecesseurDocEtat(new HashSet<>()).build();

        a.getPredecesseurDocEtat().add(b);
        b.getPredecesseurDocEtat().add(a);

        ObjectMapper mapper = new ObjectMapper();

        String json = assertDoesNotThrow(() -> mapper.writeValueAsString(a));
        assertTrue(json.contains("\"id\":\"a\""));
        assertTrue(json.contains("\"id\":\"b\""));
    }
}

