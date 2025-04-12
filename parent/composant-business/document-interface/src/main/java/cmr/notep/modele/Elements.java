package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Elements {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateSouscription;
    private Date dateModification;
    private List<ElementLangues> elementslangues ;
    private Menus menus;
    private ElementsBases elementbase;
}
