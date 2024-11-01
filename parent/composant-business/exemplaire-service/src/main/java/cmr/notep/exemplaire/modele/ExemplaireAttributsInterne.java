package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Attributs;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ExemplaireAttributsInterne {
    private String attributId;
    private String valeur;
    private Date dateCreation;
    private Date dateModification;
}
