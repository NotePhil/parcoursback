package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true,exclude = {"precoMouvementsQtes","promotions"})
@JsonIgnoreProperties({"precoMouvementsQtes","promotions"})
@EqualsAndHashCode(callSuper = true,exclude = {"precoMouvementsQtes","promotions"})
public class Distributeurs extends Personnes {
    private String id;
    private String code;
    private String raisonSociale;
    private java.util.Date dateModification;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private List<Promotions> promotions;
}
