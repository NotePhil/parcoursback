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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<String> getIdExemplairesParents() {
        return idExemplairesParents;
    }

    public void setIdExemplairesParents(List<String> idExemplairesParents) {
        this.idExemplairesParents = idExemplairesParents;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Personnes getPersonneBeneficiaire() {
        return personneBeneficiaire;
    }

    public void setPersonneBeneficiaire(Personnes personneBeneficiaire) {
        this.personneBeneficiaire = personneBeneficiaire;
    }

    public Date getDateModificated() {
        return dateModificated;
    }

    public void setDateModificated(Date dateModificated) {
        this.dateModificated = dateModificated;
    }

    public Personnes getPersonneRattachee() {
        return personneRattachee;
    }

    public void setPersonneRattachee(Personnes personneRattachee) {
        this.personneRattachee = personneRattachee;
    }

    public List<OrdreEtats> getOrdreEtats() {
        return ordreEtats;
    }

    public void setOrdreEtats(List<OrdreEtats> ordreEtats) {
        this.ordreEtats = ordreEtats;
    }

    public List<Mouvements> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<Mouvements> mouvements) {
        this.mouvements = mouvements;
    }

    public List<PersonnesDestinataires> getPersonnesDestinataires() {
        return personnesDestinataires;
    }

    public void setPersonnesDestinataires(List<PersonnesDestinataires> personnesDestinataires) {
        this.personnesDestinataires = personnesDestinataires;
    }

    public List<ExemplaireAttributs> getExemplaireAttributs() {
        return exemplaireAttributs;
    }

    public void setExemplaireAttributs(List<ExemplaireAttributs> exemplaireAttributs) {
        this.exemplaireAttributs = exemplaireAttributs;
    }
}
