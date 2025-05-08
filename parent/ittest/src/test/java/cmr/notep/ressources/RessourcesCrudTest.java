package cmr.notep.ressources;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Ressources;
import cmr.notep.utile.JsonComparator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class RessourcesCrudTest extends AbstractIttest {
    List<Ressources> ressourcesList = null;
    String dossier= "data/ressources";
    @Test
    @SneakyThrows
    @Order(1)
    public void testAvoirTousRessources() {
        ressourcesList = ressourceService.avoirToutRessources();
        String pathJson = dossier+"/ressources_avoirtous";
        ressourcesList.sort(Comparator.comparing(Ressources::getId));
        // Les noms des champs à exclure de la comparaison
        Set<String> fieldsToExclude = new HashSet<>();
        fieldsToExclude.add("id");
        fieldsToExclude.add("dateModification");
        fieldsToExclude.add("dateCreation");
        Assertions.assertTrue(JsonComparator.CompareResultWithJson(
                pathJson
                ,objectMapper.writeValueAsString(ressourcesList)
                ,Ressources[].class
                ,fieldsToExclude));
    }
}
