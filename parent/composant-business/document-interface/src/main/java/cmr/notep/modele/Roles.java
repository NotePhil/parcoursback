package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"personnels","validations"})
@EqualsAndHashCode(exclude = {"personnels","validations"})
@JsonIgnoreProperties(value = {"personnels","validations"}, ignoreUnknown = true)
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
