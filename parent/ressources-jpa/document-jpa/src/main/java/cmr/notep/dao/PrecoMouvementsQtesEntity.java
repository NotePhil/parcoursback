package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
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
    @Mapping("quantiteMin")
    @Column(name ="qtemin")
    private double qteMin ;
    @Mapping("quantiteMax")
    @Column(name ="qtemax")
    private double qteMax ;
    @Column(name =  "montantmin")
    private double montantMin ;
    @Column(name = "montantmax")
    private double montantMax ;
    @Column(name = "datecreation", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation ;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name="datemodification")
    private Date dateModification ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "precomouvements_id")
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
