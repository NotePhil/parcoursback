package cmr.notep.personnes;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.*;
import cmr.notep.utile.JsonComparator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@ContextConfiguration(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class PersonnesCrudTest extends AbstractIttest {;
 List<IPersonnes> personnesList = null;
 String dossier= "data/personnes";

@Test
@SneakyThrows
@Order(1)
 public void testAvoirTousPersonnes() {
    JavaType listOfPersonnes = objectMapper.getTypeFactory()
            .constructCollectionType(List.class, IPersonnes.class);
    personnesList = personneService.avoirToutPersonnes();
    String pathJson = dossier+"/personnes_avoirtous";
    //personnesList.sort(Comparator.comparing(Personnes::getId));
    // Les noms des champs à exclure de la comparaison
    Set<String> fieldsToExclude = new HashSet<>();
    fieldsToExclude.add("id");
    fieldsToExclude.add("dateModification");
    fieldsToExclude.add("dateCreation");
    Assertions.assertTrue(JsonComparator.CompareResultWithJson(
            pathJson
            ,objectMapper.writerFor(listOfPersonnes).writeValueAsString(personnesList)
            ,listOfPersonnes.getRawClass()
            ,fieldsToExclude));
 }

 @Test
 @SneakyThrows
 @Order(2)
 public void testPosterPersonne(){
   Distributeurs personne = Distributeurs.builder()
           .adresse("TitreTest").mail("DescriptionTest@gmail.com")
           .qrcodevalue("dededededede").telephone("1234567890").raisonSociale("RaisonSocialeTest")
           .code("CodeTest")
           .build();

    IPersonnes personne1 = personneService.posterPersonne(personne);
     JavaType listOfPersonnes = objectMapper.getTypeFactory()
             .constructCollectionType(List.class, IPersonnes.class);

   personnesList = personneService.avoirToutPersonnes();
  // PersonnesList.sort(Comparator.comparing(Personnes::getId));
   String pathJson = dossier+"/personnes_avoirtous_post";
   Set<String> fieldsToExclude = new HashSet<>();
   fieldsToExclude.add("id");
   fieldsToExclude.add("dateModification");
     fieldsToExclude.add("dateCreation");
   Assertions.assertTrue(JsonComparator.CompareResultWithJson(
           pathJson
           ,objectMapper.writerFor(listOfPersonnes).writeValueAsString(personnesList)
           ,listOfPersonnes.getRawClass()
           ,fieldsToExclude));
 }

}
