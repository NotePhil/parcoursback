package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"mouvementSoldeCaisses"})
@ToString(exclude = {"mouvementSoldeCaisses"})
@EqualsAndHashCode(exclude = {"mouvementSoldeCaisses"})
public class CaissesReelles {
    private String id;
    private Integer version;
    private double solde ;
    private Caisses caisse ;
    private Date dateCreation;
    private Date dateModification;
    private List<MouvementSoldeCaisses> mouvementSoldeCaisses;
}
