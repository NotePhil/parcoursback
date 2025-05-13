package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actions {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private ElementsBases elementsbase;
    private ActionsLangues actionslangues;
}