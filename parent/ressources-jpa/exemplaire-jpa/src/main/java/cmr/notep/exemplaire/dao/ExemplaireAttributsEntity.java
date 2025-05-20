package cmr.notep.exemplaire.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Embeddable
public class ExemplaireAttributsEntity {
    @Column(name = "attribut_id")
    private String attributId;
    @Column(name = "valeur")
    private String valeur;
    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    public ExemplaireAttributsEntity() {
    }
    public ExemplaireAttributsEntity(String attributId, String valeur, Date dateCreation, Date dateModification) {
        this.attributId = attributId;
        this.valeur = valeur;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }
}
