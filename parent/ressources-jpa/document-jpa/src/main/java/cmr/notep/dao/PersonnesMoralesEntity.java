package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personnesmorales_id")
@Table(name = "personnesmorales", schema = "document")
public class PersonnesMoralesEntity extends PersonnesEntity{

    @Column(name = "raisonsociale")
    private String raisonsociale ;

    @Column(name = "code")
    private String code;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
}
