package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"docEtats"})
@EqualsAndHashCode(exclude = {"docEtats"})
@JsonIgnoreProperties(value = {"docEtats"}, ignoreUnknown = true)
public class Etats {
    private String id;
    private String libelle;
    private String description;
    private Date dateCreation;
    private Date dateModification;
    private List<DocEtats> docEtats;
}
