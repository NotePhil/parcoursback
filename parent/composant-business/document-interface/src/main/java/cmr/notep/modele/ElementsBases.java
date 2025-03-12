package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElementsBases {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateSouscription;
    private Date dateModification;
    private String moduleAngular;
    private List<Actions> actions ;
    private List<ElementsBaseLangues> elementsbaselangues;
    private List<Elements> elements;
}
