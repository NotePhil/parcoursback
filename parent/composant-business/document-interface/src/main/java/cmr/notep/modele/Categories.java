package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

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
    private List<Associer> attributs;
    private Documents document ;
}
