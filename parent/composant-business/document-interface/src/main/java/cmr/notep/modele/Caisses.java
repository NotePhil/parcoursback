package cmr.notep.modele;

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
    private Integer version;
    private String libelle;
    private String type;
    private double solde;
    private boolean etat;
    DetailsJson detailsJson;
}
