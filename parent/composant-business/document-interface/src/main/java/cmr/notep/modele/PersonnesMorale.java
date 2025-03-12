package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonnesMorale extends Personnes {
    private String raisonsociale ;
    private String code ;
}
