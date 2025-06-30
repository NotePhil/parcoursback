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
@Table(name = "elements",schema = "login")
public class ElementsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private boolean etat;

    @Column(name = "datesouscription")
    private Date dateSouscription;

    @Column(name = "datemodification")
    private Date dateModification;

    @OneToMany(mappedBy = "elementsEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("elementslangues")
    private List<ElementLanguesEntity> elementLanguesEntities ;

    @ManyToOne
    @JoinColumn(name = "menus_id")
    @Mapping("menus")
    private MenusEntity menusEntity;

    @OneToOne
    @JoinColumn(name = "elementsbases_id", referencedColumnName = "id")
    @Mapping("elementbase")
    private ElementsBasesEntity elementsBasesEntity;

    @ManyToOne
    @JoinColumn(name = "fonctionnalites_id")
    @Mapping("fonctionnalite")
    private FonctionnalitesEntity fonctionnalitesEntity;
}
