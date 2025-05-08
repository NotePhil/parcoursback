package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caisses {
    private String id;
    private String libelle ;
    private double solde ;
    private String type ;
    private String detailJSON ;
    private List<MouvementCaisses> mouvementcaisses;
}
