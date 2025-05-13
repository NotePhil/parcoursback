package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElementsBaseLangues {
    private Langues langue;
    private ElementsBases elementbases;
    private String valeurlibelle;
}
