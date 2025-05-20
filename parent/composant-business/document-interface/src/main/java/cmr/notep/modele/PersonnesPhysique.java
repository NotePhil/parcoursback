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
public class PersonnesPhysique extends Personnes {
    private String nom ;
    private String prenom;
    private String sexe ;
    private Date dateNaissance ;
}
