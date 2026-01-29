package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"personnel"})
@EqualsAndHashCode(exclude = {"personnel"})
@JsonIgnoreProperties({"personnel"})
@Builder
public class JouerRoles {
    private String id;
    private Boolean etat ;
    private Date dateCreation;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateFin ;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateDebut ;
    private Personnels personnel ;
    private Roles role ;
}
