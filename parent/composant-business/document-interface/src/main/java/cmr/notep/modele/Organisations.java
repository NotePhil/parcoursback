package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"utilisateurs"})
@ToString(exclude = {"utilisateurs"})
@EqualsAndHashCode(exclude = {"utilisateurs"})
public class Organisations {
    private String id;
    private String raisonSociale;
    private List<Utilisateurs> utilisateurs ;
}
