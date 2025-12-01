package cmr.notep.modele;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = {"docEtats"})
@EqualsAndHashCode(exclude = {"docEtats"})
@JsonIgnoreProperties(value = {"docEtats"}, ignoreUnknown = true)
public class Documents {
    private String idDocument;

    private String titre;

    private String description;

    private boolean etat;

    private Date dateCreation;

    private Date dateModification;

    private TypeMouvement typeMouvement ;

    private Boolean afficherPrix ;

    private Boolean afficherUnite;

    private Boolean afficherDistributeur;

    private Boolean prixEditable;

    private Boolean contientRessources;

    private Boolean estencaissable;

    public List<Missions> missions ;

    private List<PrecoMouvements> precoMouvements ;

    private List<Attributs> attributs ;
    private List<Categories> categories ;
    private List<DocEtats> docEtats;
    private List<Promotions> promotions;

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Boolean getAfficherPrix() {
        return afficherPrix;
    }

    public void setAfficherPrix(Boolean afficherPrix) {
        this.afficherPrix = afficherPrix;
    }

    public Boolean getAfficherUnite() {
        return afficherUnite;
    }

    public void setAfficherUnite(Boolean afficherUnite) {
        this.afficherUnite = afficherUnite;
    }

    public Boolean getAfficherDistributeur() {
        return afficherDistributeur;
    }

    public void setAfficherDistributeur(Boolean afficherDistributeur) {
        this.afficherDistributeur = afficherDistributeur;
    }

    public Boolean getPrixEditable() {
        return prixEditable;
    }

    public void setPrixEditable(Boolean prixEditable) {
        this.prixEditable = prixEditable;
    }

    public Boolean getContientRessources() {
        return contientRessources;
    }

    public void setContientRessources(Boolean contientRessources) {
        this.contientRessources = contientRessources;
    }

    public List<Missions> getMissions() {
        return missions;
    }

    public void setMissions(List<Missions> missions) {
        this.missions = missions;
    }

    public List<PrecoMouvements> getPrecoMouvements() {
        return precoMouvements;
    }

    public void setPrecoMouvements(List<PrecoMouvements> precoMouvements) {
        this.precoMouvements = precoMouvements;
    }

    public List<Attributs> getAttributs() {
        return attributs;
    }

    public void setAttributs(List<Attributs> attributs) {
        this.attributs = attributs;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<DocEtats> getDocEtats() {
        return docEtats;
    }

    public void setDocEtats(List<DocEtats> docEtats) {
        this.docEtats = docEtats;
    }

    public List<Promotions> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotions> promotions) {
        this.promotions = promotions;
    }
}
