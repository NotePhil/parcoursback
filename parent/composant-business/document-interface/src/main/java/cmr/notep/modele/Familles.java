package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"ressources","precoMouvementsQtes"})
@ToString(exclude = {"ressources","precoMouvementsQtes"})
@EqualsAndHashCode(exclude = {"ressources","precoMouvementsQtes"})
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
