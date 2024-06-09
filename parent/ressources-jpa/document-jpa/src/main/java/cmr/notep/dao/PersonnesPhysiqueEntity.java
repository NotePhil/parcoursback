package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personnesphysique_id")
public class PersonnesPhysiqueEntity extends PersonnesEntity{

    @Column(name = "nom" , nullable = false)
    private String nom ;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "sexe")
    private String sexe ;

    @Column(name = "datenaissance",nullable = false)
    private LocalDate dateNaissance ;
}