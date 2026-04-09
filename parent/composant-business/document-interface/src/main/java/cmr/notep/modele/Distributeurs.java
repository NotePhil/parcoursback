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
    private String code;
    private String raisonSociale;
    private java.util.Date dateModification;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private List<Promotions> promotions;
}
