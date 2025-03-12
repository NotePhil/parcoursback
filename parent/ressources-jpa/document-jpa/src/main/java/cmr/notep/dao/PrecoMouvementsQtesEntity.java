package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "precomouvementsqtes", schema = "document")
public class PrecoMouvementsQtesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name ="qtemin")
    private int qteMin ;
    @Column(name ="qtemax")
    private int qteMax ;
    @Column(name =  "montantmin")
    private double montantMin ;
    @Column(name = "montantmax")
    private double montantMax ;
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation ;
    @Column(name="datemodification")
    private Date dateModification ;
    @ManyToOne
    @JoinColumn(name = "precomouvements_id" , nullable = false)
    @Mapping("precoMouvement")
    private PrecoMouvementsEntity precoMouvementsEntity ;

    @ManyToMany(mappedBy = "precoMouvementsQtesEntities", fetch = FetchType.LAZY)
    @Mapping("familles")
    private List<FamillesEntity> famillesEntities ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressources_id")
    @Mapping("ressource")
    private RessourcesEntity ressourcesEntity;

    @ManyToMany(mappedBy = "precoMouvementsQtesEntities" , fetch = FetchType.LAZY)
    @Mapping("distributeurs")
    private List<DistributeursEntity> distributeursEntities;
}
