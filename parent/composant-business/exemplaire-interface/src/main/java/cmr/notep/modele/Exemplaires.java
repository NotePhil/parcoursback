package cmr.notep.modele;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
public class Exemplaires extends Documents {
    private String id;
    private String code;
    private String codeBarre;
    private String intitule;
    //liste des id des exemplaires qui ont servi à la creation de cet exemplaire
    private List<String> idExemplairesParents;
    private Date dateCreation;
    private Date dateModification;
    private Personnes personneBeneficiaire;
    private Personnes personneRattachee;
    private List<Correspondants> personneEmettrice;
    private List<OrdreEtats> ordreEtats;
    private List<Mouvements> mouvements;
    private List<MouvementSoldeCaisses> mouvementSoldeCaisses;
    private List<Correspondants> personnesDestinataires;
    private List<ExemplaireAttributs> exemplaireAttributs;
}
