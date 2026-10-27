package cmr.notep.modele;

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
