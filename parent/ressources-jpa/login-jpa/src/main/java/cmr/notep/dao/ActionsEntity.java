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
@Table(name = "actions",schema = "login")
public class ActionsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private boolean etat;

    @Column(name = "datecreation")
    private Date dateCreation;

    @Column(name = "datemodification")
    private Date dateModification;

    @Column(name = "actionstatus")
    private String actionstatus;


    @ManyToOne
    @JoinColumn(name = "elementsbase_id")
    @Mapping("elementsbase")
    private ElementsBasesEntity elementsBasesEntity;

    @OneToMany(mappedBy = "actionsEntity",fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("actionslangues")
    private List<ActionsLanguesEntity> actionsLanguesEntities;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @Mapping("utilisateur")
    private UtilisateursEntity utilisateursEntity;
}
