package cmr.notep.exemplaire.modele;

import cmr.notep.modele.Documents;
import cmr.notep.modele.Personnes;
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
    private Date dateCreated;
    private Date dateModificated;
    private Personnes personneBeneficiaire;
    private Personnes personneRattachee;
    private List<OrdreEtats> ordreEtats;
    private List<Mouvements> mouvements;
    private List<PersonnesDestinataires> personnesDestinataires;
    private List<ExemplaireAttributs> exemplaireAttributs;

}
