package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Groupe {
    private String id;
    private String libelle;
    private String etat;
    private Menu menu;
}
