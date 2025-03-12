package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"precoMouvement", "ressource"})
@ToString(exclude = {"precoMouvement", "ressource"})
@EqualsAndHashCode(exclude = {"precoMouvement", "ressource"})
public class PrecoMouvementsQtes {
    private String id ;
    private int qteMin ;
    private int qteMax ;
    private double montantMin ;
    private double montantMax ;
    private Date dateCreation ;
    private Date dateModification ;
    private PrecoMouvements precoMouvement ;
    private List<Familles> familles;
    private Ressources ressource;
    private List<Distributeurs> distributeurs;
}
