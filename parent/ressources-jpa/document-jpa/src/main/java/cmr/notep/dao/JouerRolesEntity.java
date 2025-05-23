package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jouerroles", schema = "document")
public class JouerRolesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "etat")
    private Boolean etat ;

    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "datefin")
    private Date dateFin ;

    @Column(name = "datedebut")
    private Date dateDebut ;

    @ManyToOne
    @JoinColumn(name = "personnels_id")
    @Mapping("personnel")
    private PersonnelsEntity personnelsEntity;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    @Mapping("role")
    private RolesEntity rolesEntity;

}
