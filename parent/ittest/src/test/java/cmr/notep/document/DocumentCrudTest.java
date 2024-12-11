package cmr.notep.document;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.Documents;
import cmr.notep.modele.Types;
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
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@ContextConfiguration(classes = {ItTestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class DocumentCrudTest extends AbstractIttest {

 List<Documents> documentsList = null;
 String dossier= "data/documents";
 ObjectMapper objectMapper = new ObjectMapper();
//@Test
@SneakyThrows
@Order(1)
 public void testAvoirTousDocuments() {
   documentsList = documentService.avoirTousDocuments();
    String pathJson = dossier+"/documents_avoirtous";
   documentsList.sort(Comparator.comparing(Documents::getIdDocument));
    // Les noms des champs à exclure de la comparaison
    Set<String> fieldsToExclude = new HashSet<>();
    fieldsToExclude.add("id");
    fieldsToExclude.add("dateModification");
    fieldsToExclude.add("dateCreation");
    Assertions.assertTrue(JsonComparator.CompareResultWithJson(
            pathJson
            ,objectMapper.writeValueAsString(documentsList)
            ,Documents[].class
            ,fieldsToExclude));
 }

 //@Test
 @SneakyThrows
 //@Order(2)
 public void testPosterDocument(){
   Documents document = new Documents();
   document.setIdDocument("identifiantTest");
   document.setTitre("TitreTest");
   document.setDescription("DescriptionTest");
   document.setEtat(true);
   Attributs attribut = Attributs.builder().id("1234").etat(true).titre("TAILLE").description("taille").type(Types.String).build();
   Attributs attribut1 = Attributs.builder().id("3456").etat(true).titre("SEXES").description("SEXE").type(Types.String).build();
   document.setAttributs(List.of(attribut,attribut1));
   Documents document1 = documentService.posterDocument(document);
   documentsList = documentService.avoirTousDocuments();
  // documentsList.sort(Comparator.comparing(Documents::getId));
   String pathJson = dossier+"/documents_avoirtous_post";
   Set<String> fieldsToExclude = new HashSet<>();
   fieldsToExclude.add("id");
   fieldsToExclude.add("dateModification");
   fieldsToExclude.add("idDocument");
   Assertions.assertTrue(JsonComparator.CompareResultWithJson(
           pathJson
           ,objectMapper.writeValueAsString(documentsList)
           ,Documents[].class
           ,fieldsToExclude));
 }

    //@Test
    @SneakyThrows
    public void testPosterDocumentWithMultiThread(){
        /*Documents document = new Documents();
        document.setId("identifiantTest");
        document.setTitre("TitreTest");
        document.setDescription("DescriptionTest");
        document.setEtat(true);
        Attributs attribut = Attributs.builder().id("1234").etat(true).titre("TAILLE").description("taille").type(Types.Double).build();
        Attributs attribut1 = Attributs.builder().id("3456").etat(true).titre("SEXES").description("SEXE").type(Types.String).build();
        document.setAttributs(List.of(attribut,attribut1));

        Documents document1 = documentService.posterDocument(document);*/
        documentsList = documentService.avoirTousDocuments();
        // documentsList.sort(Comparator.comparing(Documents::getId));
        /*String pathJson = dossier+"/documents_avoirtous_post";
        Set<String> fieldsToExclude = new HashSet<>();
        fieldsToExclude.add("id");
        fieldsToExclude.add("dateModification");
        Assertions.assertTrue(JsonComparator.CompareResultWithJson(
                pathJson
                ,objectMapper.writeValueAsString(documentsList)
                ,Documents[].class
                ,fieldsToExclude));*/
        String idDoc = documentsList.get(0).getIdDocument();
        //ecrire un ajout en thread concurrent avec future et completable future
        CompletableFuture<Documents> future = CompletableFuture.supplyAsync(()->{
            Documents document2 = documentsList.get(0);
            document2.setTitre("TitreTest2");
           // Attributs attribut2 = Attributs.builder().id("12345test2").etat(true).titre("TAILLE2").description("taille2").type(Types.Double).build();
            //Attributs attribut3 = Attributs.builder().id("34567test2").etat(true).titre("SEXES2").description("SEXE2").type(Types.String).build();
            //document2.getAttributs().addAll(List.of(attribut2,attribut3));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return documentService.posterDocument(document2);
        });
        CompletableFuture<Documents> future1 = CompletableFuture.supplyAsync(()->{
            Documents document2 = documentsList.get(0);
            document2.setTitre("TitreTest3");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return documentService.posterDocument(document2);
        });
        CompletableFuture<Documents> future2 = CompletableFuture.supplyAsync(()->{
            Documents document2 = documentsList.get(0);
            document2.setTitre("TitreTest4");
            document2.setEtat(false);
            //retrait d'une categorie
           /* document2.getCategories().remove(0);
            Categories categorie = Categories.builder().id("1234-new").libelle("CategorieTest").ordre("1000").build();
            categorie.setAttributs(document2.getAttributs());
            categorie.setDocument(document2);
            document2.getCategories().add(categorie);*/

            return documentService.posterDocument(document2);
        });

        CompletableFuture.allOf(future, future1, future2).join();
        Documents doc = documentService.avoirDocument(idDoc);
        log.info("idDoc " + idDoc + " doc: "+doc);
    }

}
