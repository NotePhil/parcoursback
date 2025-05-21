package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"langue","action"})
@ToString(exclude = {"langue","action"})
@EqualsAndHashCode(exclude = {"langue","action"})
public class ActionsLangues {
    private Langues langue;
    private Actions action;
    private String valeurLibelle;
}