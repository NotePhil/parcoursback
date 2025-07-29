package cmr.notep.utile;

import cmr.notep.modele.*;
import cmr.notep.utile.tri.GenericTri;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import difflib.DiffUtils;
import difflib.Patch;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
@Slf4j
public class JsonComparator {

    public static final String SRC_TEST_RESOURCES = "src/test/resources/";
    public static final String STR_DELIMITATEUR = "\"";
    public static final String INIT_FICHIER_JSON = "";

    private  static ObjectMapper objectMapper = addDefaultPrettyPrinterToObjectMapper();

    public static boolean CompareResultWithJson(String pathJson, String resultObtenu, Class clazz, Set<String> fieldsToExclude) throws IOException {
        try {
            String urlJSonAttendu = SRC_TEST_RESOURCES.concat(pathJson).concat(".json");
            String StringJSonAttendu = getStringJSonAttendu(urlJSonAttendu);
            //ordonner les éléments à comparer puis exclure les champs spécifiés
           // ObjectMapper objectMapper = new ObjectMapper();
            JsonNode objetResultObtenu =  exclureChamps(objectMapper.writeValueAsString(
                    ordonnerElements(objectMapper.readValue(resultObtenu, clazz)))
                    , fieldsToExclude);
            JsonNode objetResultAttendu = StringJSonAttendu.equals(INIT_FICHIER_JSON) ?
                    objectMapper.readTree(StringJSonAttendu) :
                    exclureChamps(objectMapper.writeValueAsString(
                                    ordonnerElements(objectMapper.readValue(new File(urlJSonAttendu), clazz)))
                            , fieldsToExclude);;

            // Comparez les nodes Json en string et génération du patch
            boolean areEqual = compareJsonNode(objetResultAttendu, objetResultObtenu,pathJson, urlJSonAttendu,fieldsToExclude);

            if (areEqual) {
                log.info("Les fichiers JSON sont identiques.");
            } else {
                log.info("Les fichiers JSON sont différents.");
            }
            return areEqual;
        } catch (Exception e) {
            log.error("Erreur lors de la comparaison des fichiers JSON.", e);
            return false;
        }
    }

    private static String getStringJSonAttendu(String urlJSonAttendu) throws IOException {

        Path path = Paths.get(urlJSonAttendu);
        if(!Files.exists(path)){
            Files.writeString(path, INIT_FICHIER_JSON);
        }
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static Object ordonnerElements(Object objet) {
        return GenericTri.getSort(objet).sort(objet);
    }

    private static JsonNode exclureChamps(String json, Set<String> fieldsToExclude) throws IOException {
        //ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        replaceFields(jsonNode, fieldsToExclude);
        return jsonNode;
    }

    public static boolean compareJsonNode(JsonNode donneesAttendues, JsonNode resultObtenu,  String pathJson, String urlJSonAttendu,Set<String> fieldsToExclude) throws Exception {
        //transformer les données JsonNode en List de string
        List<String> donneesAttenduesList = donneesAttendues.toPrettyString().lines().toList();
        List<String> resultObtenuList = resultObtenu.toPrettyString().lines().toList();
        //sauvegarde du fichier json

        String resultObtenuChemin = SRC_TEST_RESOURCES.concat(pathJson).concat("_obtenu.json");
        //ecrireStringDansunfichier(resultObtenu, "_obtenu.json", pathJson, objectMapper);
        //ecrireStringDansunfichier(donneesAttendues, ".json", pathJson, objectMapper);

        //comparer les deux listes et creer un patch
        Patch<String> patch = DiffUtils.diff(donneesAttenduesList, resultObtenuList);
        //Generer le fichier patch depuis l'ojet patch
        List<String> patchList = DiffUtils.generateUnifiedDiff(urlJSonAttendu, urlJSonAttendu, donneesAttenduesList, patch, 0);
        if(patchList.size() != 0){
            ecrireDiffDansUnFichier(pathJson, fieldsToExclude, patchList);
        }
        return patchList.size() == 0;
    }

    private static void ecrireStringDansunfichier(JsonNode donnee, String extension,String pathJson, ObjectMapper objectMapper) throws IOException {
        String resultObtenuChemin = SRC_TEST_RESOURCES.concat(pathJson).concat(extension);
        //sauvegarde du fichier json obtenu
        //objectMapper.writeValue(new File(resultObtenuChemin), donnee);
        System.out.println(donnee.toPrettyString());
        Files.write(Paths.get(resultObtenuChemin), donnee.toPrettyString().getBytes());
    }

    private static ObjectMapper addDefaultPrettyPrinterToObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter dpp = new DefaultPrettyPrinter();
        dpp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        dpp.indentObjectsWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
       // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       // objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
       // objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature());
        objectMapper.registerSubtypes(
                new NamedType(Personnes.class,  "personne"),
                new NamedType(Distributeurs.class, "distributeur"),
                new NamedType(PersonnesPhysique.class, "personnePhysique"),
                new NamedType(Personnels.class, "personnel")
        );
        return objectMapper.setDefaultPrettyPrinter(dpp);
    }

    private static void ecrireDiffDansUnFichier(String pathJson, Set<String> fieldsToExclude, List<String> patchList) throws IOException {
        String patchChemin = SRC_TEST_RESOURCES.concat(pathJson).concat(".patch");
        String patchString = patchList.stream().collect(Collectors.joining("\n"));
        //sauvegarde du patch dans un fichier
        Files.write(Paths.get(patchChemin), patchString.getBytes());
    }

    private static void ecrireDiffDansUnFichierEnExcluant(String pathJson, Set<String> fieldsToExclude, List<String> patchList) throws IOException {
        String patchChemin = SRC_TEST_RESOURCES.concat(pathJson).concat(".patch");
        String patchString = patchList.stream().filter(x -> {
           AtomicBoolean isExcluded = new AtomicBoolean(false);
            fieldsToExclude.forEach(y -> {
                StringBuilder sb = new StringBuilder(STR_DELIMITATEUR);
                sb.append(y).append(STR_DELIMITATEUR);
                if (x.contains(sb.toString())) {
                    isExcluded.set(true);
                }
            });
            if (isExcluded.get()) {
                return false;
            } else {
                return true;
        }
        } ).collect(Collectors.joining("\n"));
        //sauvegarde du patch dans un fichier
        Files.write(Paths.get(patchChemin), patchString.getBytes());
    }

    //remplacer dans un jsonNode les champs à exclure par null
    public static void replaceFields(JsonNode node, Set<String> fieldsToExclude) {
        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (fieldsToExclude.contains(fieldName)) {
                    ((ObjectNode) node).put(fieldName, (String) null);
                }
                else {
                    replaceFields(node.get(fieldName), fieldsToExclude);
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayNode : node) {
                replaceFields(arrayNode, fieldsToExclude);
            }
        }
    }


}

