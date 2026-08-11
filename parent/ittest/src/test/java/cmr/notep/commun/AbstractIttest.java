package cmr.notep.commun;

import cmr.notep.api.*;
import cmr.notep.api.IDocEtatsApi;
import cmr.notep.api.IEtatsApi;
import cmr.notep.api.IExemplairesApi;
import cmr.notep.api.IValidationsApi;
import cmr.notep.modele.*;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("ittest")
public abstract class AbstractIttest {

  @Autowired
  protected IDocumentsApi documentService;

  @Autowired
  protected IDocEtatsApi docEtatService;

  @Autowired
  protected IEtatsApi etatsService;

  @Autowired
  protected IValidationsApi validationsService;

  @Autowired
  protected IAttributsApi attributService ;
  @Autowired
  protected IExemplairesApi exemplaireService;
  @Autowired
  protected IDistributeursApi distributeurService;
  @Autowired
  protected IPersonnesApi personneService;
  @Autowired
  protected ICategoriesApi categorieService;
  @Autowired
  protected IRessourcesApi ressourceService;
  @Autowired
  protected IPrecoMouvementsApi precoMouvementService;
  @Autowired
  protected IMissionsApi missionService;
  @Autowired
  protected IFamillesApi familleService;
  @Autowired
  protected IPromotionsApi promotionService;
  @Autowired
  protected ICaissesApi caisseService;
  protected static ObjectMapper objectMapper ;
  @BeforeAll
  public static void onInit(){
    objectMapper = new ObjectMapper();
    DefaultPrettyPrinter dpp = new DefaultPrettyPrinter();
    objectMapper.enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature());

//    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
//            .allowIfSubType("cmr.notep.modele")
//            .build();
//
//// ON active le typing par défaut sur toutes les classes non finales
//    objectMapper.activateDefaultTyping(
//            ptv,
//            ObjectMapper.DefaultTyping.NON_FINAL,
//            JsonTypeInfo.As.PROPERTY  // ou As.WRAPPER_OBJECT si vous préférez
//    );

// Optionnel : choisir le nom de la propriété
//    objectMapper.setDefaultPropertyInclusion(
//            new JsonInclude.Value(JsonTypeInfo.As.PROPERTY,
//                    JsonTypeInfo.Id.NAME,
//                    "type")
//    );
        objectMapper.registerSubtypes(
            new NamedType(Personnes.class,  "personne"),
            new NamedType(Distributeurs.class, "distributeur"),
            new NamedType(PersonnesPhysique.class, "personnePhysique"),
            new NamedType(Personnels.class, "personnel")
    );
  }
}
