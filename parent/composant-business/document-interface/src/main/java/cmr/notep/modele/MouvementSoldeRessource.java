package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

/**
 * Modèle pour les mouvements de solde des ressources
 * Les modifications de solde doivent se faire via cette entité transactionnelle
 * et ne peuvent pas être modifiées directement
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class MouvementSoldeRessource {
    private String id;
    private String ressourcesId;
    private Integer quantiteInitiale;
    private Integer quantiteMouvementee;
    private Integer quantiteFinale;
    private String typeTransaction; // "ENTREE", "SORTIE", "AJUSTEMENT"
    private String description;
    private String motif;
    private String codeReference; // Référence externe (ex: numéro de commande)
    private String createdBy;
    private Date dateCreation;
    private Boolean validee;
    private String valideeBy;
    private Date dateValidation;
    private String statut; // "EN_ATTENTE", "VALIDEE", "REJETEE"
}
