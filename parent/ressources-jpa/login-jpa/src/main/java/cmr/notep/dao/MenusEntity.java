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
@Table(name = "menus",schema = "login")
public class MenusEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "etat")
    private boolean etat;

    @Column(name = "datecreation")
    private Date dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateurs_id", referencedColumnName = "id")
    @Mapping("utilisateur")
    private UtilisateursEntity utilisateursEntity ;

    @OneToOne
    @JoinColumn(name = "groupes_id")
    @Mapping("groupe")
    private GroupesEntity groupesEntity ;

    @OneToMany(mappedBy = "menusEntity")
    @Mapping("elements")
    private List<ElementsEntity> elementsEntities;
}
