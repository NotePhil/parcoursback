package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
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
    @Column(name = "datemodification")
    private Date dateModification;
}
