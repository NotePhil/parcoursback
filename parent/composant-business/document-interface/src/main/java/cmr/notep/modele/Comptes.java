package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"beneficiaire"},ignoreUnknown = true)
public class Comptes {
    private String id;
    private String libelle;
    private Integer montantDecouvertMax;
    private Integer solde;
    private Boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private PersonnesPhysique beneficiaire;
}