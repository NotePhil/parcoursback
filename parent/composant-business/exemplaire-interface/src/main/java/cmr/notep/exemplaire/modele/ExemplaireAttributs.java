package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Attributs;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ExemplaireAttributs {
    private Attributs attribut;
    private String valeur;
    private Date dateCreation;
    private Date dateModification;
}
