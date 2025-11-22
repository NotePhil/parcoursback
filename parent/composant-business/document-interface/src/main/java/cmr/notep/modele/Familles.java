package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"ressources","precoMouvementsQtes","promotions"})
@ToString(exclude = {"ressources","precoMouvementsQtes","promotions"})
@EqualsAndHashCode(exclude = {"ressources","precoMouvementsQtes","promotions"})
public class Familles {
    private String id ;
    private String libelle;
    private String description;
    private Boolean etat ;
    private Date dateCreation;
    private Date dateModification;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private List<Ressources> ressources;
    private List<Promotions> promotions;
}
