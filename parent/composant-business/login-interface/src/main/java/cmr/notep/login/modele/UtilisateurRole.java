package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilisateurRole {
    private String id;
    private Role role;
    private Boolean status;
    private Date dateDebut;
    private Date dateFin;
}
