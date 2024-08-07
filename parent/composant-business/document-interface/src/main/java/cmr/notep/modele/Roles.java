package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {
    private String id;
    private String titre;
    private String description;
    private Boolean etat ;
    private Date dateCreation;
    private List<JouerRoles> personnels;
    private List<Remplir> missions;
    private List<Validations> validations;
}
