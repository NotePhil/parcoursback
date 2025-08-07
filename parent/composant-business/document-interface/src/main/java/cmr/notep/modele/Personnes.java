package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonnesPhysique.class, name = "personnePhysique"),
        @JsonSubTypes.Type(value = PersonnesMorale.class, name = "personneMorale"),
        @JsonSubTypes.Type(value = Personnels.class, name = "personnel"),
        @JsonSubTypes.Type(value = Distributeurs.class, name = "distributeur"),
})

public class Personnes implements Serializable, IPersonnes {
    private String id ;
    private String adresse ;
    private String mail ;
    private boolean etat;
    private  String telephone ;
    private  String qrcodevalue ;
    private Comptes compte;
    private String type;
    private Tickets ticket;
    private Exemplaires exemplaires;
    private Date dateCreation;
    private Date dateModification;
    private List<IPersonnes> personnesRatachees = new ArrayList<>();
}
