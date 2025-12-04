package cmr.notep.exemplaire;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exemplaire.modele.Exemplaires;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.TypeAttribut;
import cmr.notep.modele.Types;
import cmr.notep.utile.JsonComparator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@ContextConfiguration(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class ExemplaireCrudTest extends AbstractIttest {

 List<Exemplaires> exemplairesList = null;
 String dossier= "data/exemplaire";
//@Test
@SneakyThrows
 public void testAvoirTousExemplaires() {

   exemplairesList = exemplaireService.avoirTousExemplaires();
    String pathJson = dossier+"/exemplaires_avoirtous";
   exemplairesList.sort(Comparator.comparing(Exemplaires::getId));
    // Les noms des champs à exclure de la comparaison
    Set<String> fieldsToExclude = new HashSet<>();
    fieldsToExclude.add("id");
    fieldsToExclude.add("dateModification");
    fieldsToExclude.add("dateCreation");
    Assertions.assertTrue(JsonComparator.CompareResultWithJson(
            pathJson
            ,objectMapper.writeValueAsString(exemplairesList)
            ,Exemplaires[].class
            ,fieldsToExclude));
 }

 //@Test
 @SneakyThrows
 public void testPosterExemplaire(){
   var exemplaires = exemplaireService.avoirTousExemplaires();
   Exemplaires document = new Exemplaires();
   document.setId("identifiantTest");
   document.setTitre("TitreTest");
   document.setDescription("DescriptionTest");
   document.setEtat(true);
   Attributs attribut = Attributs.builder().id("1234").etat(true).titre("TAILLE").description("taille").type_attribut(TypeAttribut.Text).build();
   Attributs attribut1 = Attributs.builder().id("3456").etat(true).titre("SEXES").description("SEXE").type_attribut(TypeAttribut.Text).build();
   document.setAttributs(List.of(attribut,attribut1));
  // Exemplaires document1 = documentService.posterExemplaire(document);
  // exemplairesList = documentService.avoirTousExemplaires();
  // exemplairesList.sort(Comparator.comparing(Exemplaires::getId));
   String pathJson = dossier+"/exemplaires_avoirtous_post";
   Set<String> fieldsToExclude = new HashSet<>();
   fieldsToExclude.add("id");
   fieldsToExclude.add("dateModification");
   Assertions.assertTrue(JsonComparator.CompareResultWithJson(
           pathJson
           ,objectMapper.writeValueAsString(exemplairesList)
           ,Exemplaires[].class
           ,fieldsToExclude));
 }

}
