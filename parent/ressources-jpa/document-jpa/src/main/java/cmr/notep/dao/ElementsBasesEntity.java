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
@Table(name = "elementsbases")
public class ElementsBasesEntity {

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

    @Column(name = "moduleangular")
    private String moduleAngular;

    @OneToMany(mappedBy = "elementsBasesEntity")
    @Mapping("actions")
    private List<ActionsEntity> actionsEntityList ;

    @OneToMany(mappedBy = "elementsBasesEntity" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("elementsbaselangues")
    private List<ElementsBaseLanguesEntity> elementsBaseLanguesEntities;

    @OneToMany(mappedBy = "elementsBasesEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    @Mapping("elements")
    private List<ElementsEntity> elementsEntities;
}
