package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Personnels extends Personnes {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateEntree ;
    private String nom;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private  Date dateNaissance ;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateSortie;
    private String prenom;
    private String sexe ;
    private List<JouerRoles> jouerroles;
}
