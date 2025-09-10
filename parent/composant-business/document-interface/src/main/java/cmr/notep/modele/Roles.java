package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"jouerroles"}, ignoreUnknown = true)
@ToString(exclude = { "jouerroles"})
@EqualsAndHashCode(exclude = { "jouerroles"})
public class Roles {
    private String id;
    private String titre;
    private String description;
    private Boolean etat ;
    private Date dateCreation;
    private Date dateModification;
    private List<JouerRoles> jouerroles;
    private List<Remplir> remplirs;
    private List<Validations> validations;
}
