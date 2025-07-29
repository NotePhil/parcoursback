package cmr.notep.modele;

import cmr.notep.exemplaire.modele.EtatsValidations;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class OrdreEtats {
    private String id;
    private Date dateCreation;
    private Date dateModification;
    private Date dateFinVote;
    private int ordre ;
    private Etats etat;
    private List<EtatsValidations> etatsValidations;
}
