package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"element"})
@ToString(exclude = {"element"})
@EqualsAndHashCode(exclude = {"element"})
public class ElementsBases {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateSouscription;
    private Date dateModification;
    private String moduleAngular;
    private List<Actions> actions ;
    private List<ElementsBaseLangues> elementsbaselangues;
    private Elements element;
}
