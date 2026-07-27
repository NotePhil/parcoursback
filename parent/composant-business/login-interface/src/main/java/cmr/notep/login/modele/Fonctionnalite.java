package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fonctionnalite {
    private String id;
    private String fonction;
    private String icone;
    private String actif;
    private Integer ordre;
    private List<Element> elements;
}
