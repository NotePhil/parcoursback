package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Element {
    private String id;
    private String nom;
    private String lien;
    private String bouton;
    private Integer ordre;
    private List<Action> action;
}
