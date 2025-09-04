package cmr.notep.modele;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PersonnesMorale extends Personnes {
    private String raisonsociale ;
    private String code ;
}
