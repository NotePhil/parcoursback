package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"exemplaire"})
@ToString(  exclude = {"exemplaire"})
@EqualsAndHashCode(exclude = {"exemplaire"})
public class MouvementSoldeCaisses {

    private String id;
    private CaissesReelles caissesReelles;
    private Double soldeInitiale;
    private Double soldeMovementee;
    private Double soldeFinale;
    private String typeTransaction; // DEBIT, CREDIT, AJUSTEMENT
    private String description;
    private String motif;
    private String codeReference;
    private String createdBy;
    private Date dateCreation;
    private Boolean validee;
    private String valideeBy;
    private Date dateValidation;
    private String statut; // EN_ATTENTE, VALIDEE, REJETEE
    private Exemplaires exemplaire;
}
