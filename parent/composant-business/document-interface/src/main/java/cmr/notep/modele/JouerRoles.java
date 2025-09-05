package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"personnel","role"})
@EqualsAndHashCode(exclude = {"personnel","role"})
@JsonIgnoreProperties({"personnel","role"})
public class JouerRoles {
    private String id;
    private Boolean etat ;
    private Date dateCreation;
    private Date dateModification;
    private Date dateFin ;
    private Date dateDebut ;
    private Personnels personnel ;
    private Roles role ;
}
