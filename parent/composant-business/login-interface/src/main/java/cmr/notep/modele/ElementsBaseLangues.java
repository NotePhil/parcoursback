package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"langue","elementbases"})
@ToString(exclude = {"langue","elementbases"})
@EqualsAndHashCode(exclude = {"langue","elementbases"})
public class ElementsBaseLangues {
    private Langues langue;
    private ElementsBases elementbases;
    private String valeurlibelle;
}
