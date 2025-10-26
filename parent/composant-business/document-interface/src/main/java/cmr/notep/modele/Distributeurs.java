package cmr.notep.modele;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Distributeurs extends Personnes {
    private Date dateModification;
    private String code ;
    private String raisonSociale;
    private List<Mouvements> mouvements;
    private List<PrecoMouvementsQtes> precomouvementsqtes;
    private List<Promotions> promotions;
}
