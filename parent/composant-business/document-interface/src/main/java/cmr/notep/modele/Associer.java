package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"categorie"})
@EqualsAndHashCode(exclude = {"categorie"})
@JsonIgnoreProperties({"categorie"})
public class Associer {
    private  AssocierId id;
    private  Attributs attribut ;
    private  Categories categorie ;

    private boolean obligatoire ;

    private int ordre ;
}
