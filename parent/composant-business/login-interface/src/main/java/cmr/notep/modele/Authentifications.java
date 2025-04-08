package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authentifications {

    private String id;
    private String login;
    private String password;
    private Boolean authStatus;
    private Date dateAuth;
    private Utilisateurs utilisateur ;
}
