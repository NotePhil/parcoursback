package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdreEtats {
    private String id;
    private Date dateCreation;
    private Date dateModification;
    private Date dateFinVote;
    private int ordre ;
    private Etats etat;
}