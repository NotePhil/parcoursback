package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Etats;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"exemplairesInterne"})
@EqualsAndHashCode(exclude = {"exemplairesInterne"})
@JsonIgnoreProperties(value = {"exemplairesInterne"})
public class OrdreEtatsInterne {
    private String id;
    private Date dateCreation;
    private Date dateModification;
    private Date dateFinVote;
    private int ordre ;
    private String etatsId;
    private List<EtatsValidationsInterne> etatsValidationsInternes;
    private ExemplairesInterne exemplairesInterne;
}
