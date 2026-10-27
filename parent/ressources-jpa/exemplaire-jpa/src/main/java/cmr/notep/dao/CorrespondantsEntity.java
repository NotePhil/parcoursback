package cmr.notep.dao;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

/**
 * CorrespondantsEntity is an embeddable class that represents the correspondent information for a person.
 * It contains fields for the person's ID, the date of exchange, and the method used.
 */
@Getter
@Setter
@Embeddable
public class CorrespondantsEntity {
    @Column(name = "personneid")
    private String personneId;
    @Column(name = "dateechange", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEchange;
    @Column(name = "methodeutilisee")
    private String methodeUtilisee;
}
