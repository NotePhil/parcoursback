package cmr.notep.modele;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Distributeurs extends Personnes {
    private String id;
    private List<PrecoMouvementsQtes> precomouvementsqtes;
    private String raisonSociale;
    private String code ;
    private List<Promotions> promotions;
    private Mouvements mouvements;
}
