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

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEtat() {
        return etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public TypeAttribut getType_attribut() {
        return type_attribut;
    }

    public void setType(TypeAttribut type_attribut) {
        this.type_attribut = type_attribut;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public String getValeurParDefaut() {
        return valeurParDefaut;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

}
