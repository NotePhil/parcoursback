package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"attribut","categorie"})
@EqualsAndHashCode(exclude = {"attribut","categorie"})
@JsonIgnoreProperties({"attribut","categorie"})
public class Associer {

    private  Attributs attribut ;

    private Categories categorie;

    private boolean obligatoire ;

    private int ordre ;
}
