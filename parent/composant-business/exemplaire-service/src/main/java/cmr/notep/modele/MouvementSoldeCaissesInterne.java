package cmr.notep.modele;

import cmr.notep.dao.ExemplairesEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties()
@ToString()
@EqualsAndHashCode()
public class MouvementSoldeCaissesInterne {

    private String id;
    private CaissesReellesInterne caissesReellesInterne;
    private Double soldeInitiale;
    private Double soldeMovementee;
    private Double soldeFinale;
    private String typeTransaction; // DEBIT, CREDIT, AJUSTEMENT
    private String description;
    private String motif;
    private String codeReference;
    private String createdBy;
    private Date dateCreation;
    private Boolean validee = false;
    private String valideeBy;
    private Date dateValidation;
    private String statut; // EN_ATTENTE, VALIDEE, REJETEE
    // Alias conservé pour compatibilité avec les mappings Dozer historiques (@Mapping("exemplaireInterne")).
    private ExemplairesInterne exemplaireInterne;
}
