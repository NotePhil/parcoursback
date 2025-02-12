package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "remplir", schema = "document")
public class RemplirEntity{

    @EmbeddedId
    private RemplirEntityID id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    @Mapping("role")
    private RolesEntity rolesEntity;

    @ManyToOne
    @MapsId("missionId")
    @JoinColumn(name = "missions_id", referencedColumnName = "id")
    @Mapping("mission")
    private MissionsEntity missionsEntity;

    @Column(name = "datefin")
    private Date dateFin ;

    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;

    @Column(name = "datedebut")
    private Date dateDebut;

    @Column(name = "etat")
    private boolean etat ;

    @Column(name = "droitajouter")
    private boolean droitAjouter;

    @Column(name = "droitmodifier")
    private boolean droitModifier;

    @Column(name = "droitconsulter")
    private boolean droitConsulter;

    @Column(name = "droitvalider")
    private boolean droitDeValider;
}
