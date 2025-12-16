package cmr.notep.modele;

import cmr.notep.modele.DetailsJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties()
@ToString()
@EqualsAndHashCode()
public class Caisses {
    private String id;
    private String libelle;
    private String type;
    private double solde;
    private boolean etat;
    DetailsJson detailjson;
}
