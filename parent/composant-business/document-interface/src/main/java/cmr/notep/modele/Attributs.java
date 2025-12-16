package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"documents"},ignoreUnknown = true)
@ToString(exclude = {"documents"})
@EqualsAndHashCode(exclude = {"documents"})
public class Attributs {
    private String id;
    private String titre;

    private String description;

    private boolean etat;

    private Date dateCreation;

    private Date dateModification;

    TypeAttribut type_attribut;

    private String valeurParDefaut;

    private List<Documents> documents;
    //  private List<Associer> categories ;

}
