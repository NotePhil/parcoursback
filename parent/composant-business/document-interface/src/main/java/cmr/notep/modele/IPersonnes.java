package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Personnes.class, name = "personne"),
        @JsonSubTypes.Type(value = Distributeurs.class, name = "distributeur"),
        @JsonSubTypes.Type(value = PersonnesPhysique.class, name = "personnePhysique"),
        @JsonSubTypes.Type(value = Personnels.class, name = "personnel"),
})
public interface IPersonnes {
}
