package cmr.notep.dao;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "personnels_id")
@Table(name = "personnels", schema = "document")
public class PersonnelsEntity extends PersonnesEntity {

    @Column(name = "dateentree")
    private Date dateEntree ;

    @Column(name = "nom")
    private String nom;

    @Column(name = "datenaissance")
    private  Date dateNaissance ;

    @Column(name = "datesortie")
    private Date dateSortie;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "sexe")
    private String sexe ;

    @OneToMany(mappedBy = "personnelsEntity", fetch = FetchType.LAZY)
    @Mapping("roles")
    List<JouerRolesEntity> jouerRolesEntities;

    @OneToMany(mappedBy = "personnelsEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("mouvementcaisses")
    List<MouvementCaissesEntity> mouvementCaissesEntities;

}
