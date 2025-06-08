package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personnesphysique_id")
@Table(name = "personnesphysique", schema = "document")
public class PersonnesPhysiquesEntity extends PersonnesEntity{

    @Column(name = "nom" , nullable = false)
    private String nom ;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "sexe")
    private String sexe ;

    @Column(name = "datenaissance",nullable = false)
    private Date dateNaissance ;
    @Column(name = "datemodification")
    private Date dateModification;
}
