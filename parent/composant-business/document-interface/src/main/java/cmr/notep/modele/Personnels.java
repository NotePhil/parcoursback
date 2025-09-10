package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(value = {"jouerroles"})
@ToString(exclude = { "jouerroles"})
@EqualsAndHashCode(exclude = { "jouerroles"}, callSuper = false)
public class Personnels extends Personnes {
    private Date dateEntree ;
    private String nom;
    private  Date dateNaissance ;
    private Date dateSortie;
    private String prenom;
    private String sexe ;
    private List<JouerRoles> jouerroles;
}
