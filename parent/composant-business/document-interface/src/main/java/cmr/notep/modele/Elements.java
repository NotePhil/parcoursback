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
public class Elements {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateSouscription;
    private Date dateModification;
    private ElementLangues elementslangues;
    private Menus menus;
    private ElementsBases elementbase;
}
