package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@DiscriminatorValue("personnesmorales")
public class PersonnesMoralesEntity extends PersonnesEntity{

    @Column(name = "raisonsociale")
    private String raisonsociale ;

    @Column(name = "code")
    private String code;
}
