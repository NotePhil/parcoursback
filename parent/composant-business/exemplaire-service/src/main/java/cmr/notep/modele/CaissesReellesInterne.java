package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"mouvementSoldeCaissesInternes"})
@ToString(exclude = {"mouvementSoldeCaissesInternes"})
@EqualsAndHashCode(exclude = {"mouvementSoldeCaissesInternes"})
public class CaissesReellesInterne {

    private String id;
    private Integer version;
    private double solde ;
    private String caisseId ;
    private Date dateCreation;
    private Date dateModification;
    private List<MouvementSoldeCaissesInterne> mouvementSoldeCaissesInternes;
}
