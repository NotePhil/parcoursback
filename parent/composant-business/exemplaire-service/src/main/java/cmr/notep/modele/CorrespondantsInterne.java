package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class CorrespondantsInterne {
    private String personneId;
    private Date dateEchange;
    private String methodeUtilisee;
}


