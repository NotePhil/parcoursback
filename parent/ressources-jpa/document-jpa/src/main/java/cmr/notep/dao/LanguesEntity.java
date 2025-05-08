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
@Table(name = "langues")
public class LanguesEntity {
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

    @OneToMany(mappedBy = "languesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("elementslangues")
    private List<ElementLanguesEntity> elementLanguesEntities ;

    @OneToMany(mappedBy = "languesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("elementsbase")
    private List<ElementsBaseLanguesEntity> elementsBaseLanguesEntities ;

    @OneToMany(mappedBy = "languesEntity" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("actionslangues")
    private List<ActionsLanguesEntity> actionsLanguesEntities;
}
