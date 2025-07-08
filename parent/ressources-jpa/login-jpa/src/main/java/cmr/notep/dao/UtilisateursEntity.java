package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "utilisateurs",schema = "login")
public class UtilisateursEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "login",unique = true)
    private String login;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "roles")
    private String roles;

    @Column(name = "mdp")
    private String mdp;

    @Column(name = "etat")
    private boolean etat;

    @Column(name = "datecreation")
    private Date dateCreation;

    @Column(name = "datemodification")
    private Date dateModification;

    @ManyToOne
    @JoinColumn(name = "groupes_id")
    @Mapping("groupe")
    private GroupesEntity groupesEntity ;

    @ManyToMany(mappedBy = "utilisateursEntities")
    @Mapping("organisations")
    private List<OrganisationsEntity> organisationsEntityList ;

    @OneToMany(mappedBy = "utilisateursEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Mapping("menus")
    private List<MenusEntity> menusEntities;

}
