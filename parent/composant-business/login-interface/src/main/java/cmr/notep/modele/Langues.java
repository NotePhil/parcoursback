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
public class Langues {
    private  String id ;
    private String libelle;
    private boolean etat;
    private Date dateSouscription;
    private Date dateModification;
    private ElementLangues elementslangues;
    private ElementsBaseLangues elementsbase;
    private ActionsLangues actionslangues;
}