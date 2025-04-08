package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Column(name = "login")
    private String login;

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

    @OneToOne
    @JoinColumn(name = "menus_id" , referencedColumnName = "id")
    @Mapping("menus")
    private MenusEntity menusEntity;

    @OneToMany(mappedBy = "utilisateursEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("tokens")
    private List<TokensEntity> tokenEntityList;

    @OneToOne
    @MapsId
    @JoinColumn(name = "authentification_id")
    @Mapping("authentification")
    private AuthentificationsEntity authentificationEntity ;

    @OneToMany(mappedBy = "utilisateursEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("actions")
    private List<ActionsEntity> actionsEntityList;
}
