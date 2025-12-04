package cmr.notep.attributs;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.TypeAttribut;
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

import java.util.*;

@SpringBootTest
@ContextConfiguration(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class AttributsCrudTest extends AbstractIttest {

 List<Attributs> attributsList = null;
 String dossier= "data/attributs";
@Test
@SneakyThrows
@Order(1)
 public void testAvoirTousAttributs() {
   attributsList = attributService.avoirToutAttribut();
    String pathJson = dossier+"/attributs_avoirtous";
   attributsList.sort(Comparator.comparing(Attributs::getId));
    // Les noms des champs à exclure de la comparaison
    Set<String> fieldsToExclude = new HashSet<>();
    fieldsToExclude.add("id");
    fieldsToExclude.add("dateModification");
    fieldsToExclude.add("dateCreation");
    Assertions.assertTrue(JsonComparator.CompareResultWithJson(
            pathJson
            ,objectMapper.writeValueAsString(attributsList)
            ,Attributs[].class
            ,fieldsToExclude));
 }

 @Test
 @SneakyThrows
 @Order(2)
 public void testPosterAttribut(){
   Attributs attribut = new Attributs();
     attribut.setId("identifiantTest");
     attribut.setDateCreation(new Date());
     attribut.setEtat(true);
    attribut.setTitre("TitreTest");
    attribut.setDescription("DescriptionTest");
    attribut.setType_attribut(TypeAttribut.Checkbox);
    attribut.setValeurParDefaut("valeurParDefaut");

   //Attributs attribut = Attributs.builder().id("1234").etat(true).titre("TAILLE").description("taille").type(Types.String).build();
   //Attributs attribut1 = Attributs.builder().id("3456").etat(true).titre("SEXES").description("SEXE").type(Types.String).build();
   //personne.setAttributs(List.of(attribut,attribut1));
   Attributs attribut1 = attributService.posterAttribut(attribut);


   attributsList = attributService.avoirToutAttribut();
  // attributsList.sort(Comparator.comparing(Attributs::getId));
   String pathJson = dossier+"/attributs_avoirtous_post";
   Set<String> fieldsToExclude = new HashSet<>();
   fieldsToExclude.add("id");
   fieldsToExclude.add("dateModification");
     fieldsToExclude.add("dateCreation");
   Assertions.assertTrue(JsonComparator.CompareResultWithJson(
           pathJson
           ,objectMapper.writeValueAsString(attributsList)
           ,Attributs[].class
           ,fieldsToExclude));
 }

}
