package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personnels {
    private String id;
    private Date dateEntree ;
    private String nom;
    private  Date dateNaissance ;
    private String telephone;
    private Date dateSortie;
    private String email;
    private String prenom;
    private String sexe ;
    private Date dateModification;
    private List<JouerRoles> roles;
    private List<MouvementCaisses> mouvementcaisses;
}
