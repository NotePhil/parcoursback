package cmr.notep.login.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "utilisateur_roles", schema = "login")
public class UtilisateurRoleEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "utilisateur_id", nullable = false)
    private String utilisateurId;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "datedebut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    @Column(name = "datefin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
}
