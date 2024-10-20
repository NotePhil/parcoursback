package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Documents;
import cmr.notep.modele.Personnes;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*@Builder*/
@ToString
@EqualsAndHashCode
public class Exemplaires  {
    private String id;
    private String code;
    private String codeBarre;
    private String titre;
    //liste des id des exemplaires qui ont servi à la creation de cet exemplaire
    private List<String> idExemplairesParents;
    private Date dateCreation;
    private Date dateModification;
    private Personnes personneBeneficiaire;
    private Personnes personneRattachee;
    private List<OrdreEtats> ordreEtats;
    private List<Mouvements> mouvements;
    private List<PersonnesDestinataires> personnesDestinataires;
    private List<ExemplaireAttributs> exemplaireAttributs;

}
