package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"documents"},ignoreUnknown = true)
//@ToString(exclude = {"documents"})
//@EqualsAndHashCode(exclude = {"documents"})
public class Comptes {
    private String id;
    private int solde;
    private String libelle;
    private Date dateCreation;
    private PersonnesPhysique beneficiaire;
    private int montantDecouvertMax;
    private boolean etat;
}