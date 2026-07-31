package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"precoMouvementsQtes", "ressources","promotions"})
@ToString(exclude = {"precoMouvementsQtes","ressources","promotions"})
@EqualsAndHashCode(exclude = {"precoMouvementsQtes","ressources","promotions"})
public class Familles {
    private String id ;
    private String libelle;
    private String description;
    private String etat ;
    private Date dateCreation;
    private Date dateModification;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private List<Ressources> ressources;
    private List<Promotions> promotions;
}
