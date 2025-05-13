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
@ToString(exclude = {"document"})
@EqualsAndHashCode(exclude = {"document"})
@JsonIgnoreProperties({"document"})
public class Categories {
    private String id;
    private String ordre;
    private String libelle;
    private Date dateCreation;
    private Date dateModification;
    private List<Associer> associer_attributs;
    private Documents document ;
}
