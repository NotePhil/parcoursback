package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "caissesreelles",schema = "exemplaire")
public class CaissesReellesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name="solde",nullable = false)
    private double solde ;

    @Column(name="caisse_id",nullable = false)
    private String caisseId ;

    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;

    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;

    @OneToMany(mappedBy = "caissesEntity", fetch = FetchType.LAZY)
    @Mapping("mouvementSoldeCaissesInternes")
    private List<MouvementSoldeCaissesEntity> mouvementSoldeCaissesEntities;
}
