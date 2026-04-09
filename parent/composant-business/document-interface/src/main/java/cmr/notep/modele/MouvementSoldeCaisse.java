package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class MouvementSoldeCaisse 
{
    String caissesId;
    Double soldeMovementee;
    String typeTransaction;
    String description;
    String motif;
    String codeReference;
    String createdBy;
}
