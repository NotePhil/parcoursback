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
public class Personnels extends Personnes {
    private String personnels_id;
    private Date dateEntree ;
    private String nom;
    private  Date dateNaissance ;
    private Date dateSortie;
    private String prenom;
    private String sexe ;
    private List<JouerRoles> roles;
    private List<MouvementCaisses> mouvementcaisses;
}
