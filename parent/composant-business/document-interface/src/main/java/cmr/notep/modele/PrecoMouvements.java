package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"documents"})
@EqualsAndHashCode(exclude = {"documents"})
@JsonIgnoreProperties({"documents"})
public class PrecoMouvements {
    private String id ;
    private String libelle ;
    private boolean etat ;
    private TypeMouvement typeMouvement ;
    private Date dateCreation ;
    private Date dateModification ;
    private List<Mouvements> mouvements ;
    private List<Documents> documents ;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
}
