package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Utilisateur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private String sexe;
    private Date dateNaissance;
    private Date dateEntree;
    private String type;
    private List<UtilisateurRole> roles;
    private String qrCodeValue;
    private String personnelId;
    private String groupeId;
}
