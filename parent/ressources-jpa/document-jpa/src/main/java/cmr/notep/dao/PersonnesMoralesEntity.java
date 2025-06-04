package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("personnesmorales")
@Table(name = "personnesmorales", schema = "document")
public class PersonnesMoralesEntity extends PersonnesEntity{

    @Column(name = "raisonsociale")
    private String raisonsociale ;

    @Column(name = "code")
    private String code;
}
