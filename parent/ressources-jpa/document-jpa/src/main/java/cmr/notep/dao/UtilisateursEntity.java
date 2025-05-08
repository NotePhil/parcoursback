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
@Table(name = "utilisateurs")
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
}
