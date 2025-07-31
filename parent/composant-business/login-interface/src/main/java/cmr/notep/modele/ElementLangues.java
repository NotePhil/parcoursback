package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"element","langue"})
@ToString(exclude = {"element","langue"})
@EqualsAndHashCode(exclude = {"element","langue"})
public class ElementLangues {
    private Langues langue;
    private Element  element ;
    private String valeurLibelle ;
}