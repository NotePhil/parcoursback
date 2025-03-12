package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"personne"})
@ToString(exclude = {"personne"})
@EqualsAndHashCode(exclude = {"personne"})
public class Comptes {
    private String id;
    private double solde ;
    private Date dateCreation ;
    private String etat ;
    private double montantDecouvertMax ;
    private String libelle ;
    private Personnes personne;
    private List<MouvementCaisses> mouvementcaisses;
    private List<DeltaSoldes> deltasoldes;
}
