package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private Date dateModification;
    private List<JouerRoles> personnels;
    private List<Remplir> missions;
    private List<Validations> validations;
}