package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"caisse","compte","personnel","exemplaire"})
@EqualsAndHashCode(exclude = {"caisse","compte","personnel","exemplaire"})
@JsonIgnoreProperties({"caisse","compte","personnel","exemplaire"})
public class MouvementCaisses {
    private String id;
    private double montant ;
    private String moyenPaiement;
    private String referencePaiement;
    private String detailJSON;
    private Date dateCreation ;
    private String typeMvt;
    private String libelle;
    private String etat;
    private Caisses caisse;
    private Comptes compte ;
    private Personnels personnel;
    private Exemplaires exemplaire;
}
