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

    @Column(name = "langue")
    private String langue;

    @Column(name = "datecreation")
    private Date dateCreation;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Mapping("utilisateur")
    private UtilisateursEntity utilisateursEntity ;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    @Mapping("groupe")
    private GroupesEntity groupesEntity ;

    @OneToOne
    @JoinColumn(name = "element_id", referencedColumnName = "id")
    @Mapping("element")
    private ElementsEntity elementsEntity;

    @OneToMany(mappedBy = "menusEntity")
    @Mapping("fonctionnalites")
    private List<FonctionnalitesEntity> fonctionnalitesEntities;
}
