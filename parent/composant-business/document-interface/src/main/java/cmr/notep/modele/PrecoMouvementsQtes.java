package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "precoMouvement"})
@ToString(exclude = {"precoMouvement"})
@EqualsAndHashCode(exclude = {"precoMouvement"})
public class PrecoMouvementsQtes {
    private String id ;
    private int qteMin ;
    private int qteMax ;
    private double montantMin ;
    private double montantMax ;
    private PrecoMouvements precoMouvement;
    private List<Familles> familles;
    private Ressources ressource;
    private List<Distributeurs> distributeurs;
}
