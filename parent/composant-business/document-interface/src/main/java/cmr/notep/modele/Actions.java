package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"elementsbase"})
@ToString(exclude = {"elementsbase"})
@EqualsAndHashCode(exclude = {"elementsbase"})
public class Actions {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private ElementsBases elementsbase;
    private List<ActionsLangues> actionslangues;
}