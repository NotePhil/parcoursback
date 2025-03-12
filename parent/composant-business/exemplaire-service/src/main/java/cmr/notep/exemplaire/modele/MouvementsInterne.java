package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Distributeurs;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.Ressources;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"exemplaire"})
@ToString(exclude = {"exemplaire"})
@EqualsAndHashCode(exclude = {"exemplaire"})
public class MouvementsInterne {
    private  String id ;
    private  String description ;
    private int quantite ;
    private double prix ;
    private Date dateCreation ;
    private Date datePeremption ;
    private List<String> precoMouvementsRespecterIds;
    private List<String> precoMouvementsViolerIds;
    private String ressourcesId;
    private String distributeursId;
    private ExemplairesInterne exemplaireinterne;
}
