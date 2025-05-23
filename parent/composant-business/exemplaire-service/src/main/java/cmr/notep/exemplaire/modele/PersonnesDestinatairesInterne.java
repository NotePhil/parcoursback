package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Personnes;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PersonnesDestinatairesInterne {
    private String personneId;
    private Date dateEnvoi;
    private String methodeEnvoi;
}
