package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String id;
    private String login;
    private Utilisateur user;
    private Groupe groupe;
}
