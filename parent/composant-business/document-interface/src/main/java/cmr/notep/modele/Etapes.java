package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"parcour"})
@ToString(exclude = {"parcour"})
@EqualsAndHashCode(exclude = {"parcour"})
public class Etapes {
    private String id;
    private String libelle;
    private String etat;
    private Date dateModification;
    private Parcours parcour;
    private List<DocEtats> docEtats;
}
