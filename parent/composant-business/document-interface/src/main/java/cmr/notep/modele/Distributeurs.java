package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.maven.artifact.DefaultArtifact;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Distributeurs extends Personnes {

    private Date dateModification;
    private List<PrecoMouvementsQtes> precomouvementsqtes;
    private String raisonSociale;
    private String code ;
    private List<Promotions> promotions;
    private Mouvements mouvements; 
}
