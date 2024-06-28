package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "distributeurs_id")
@Table(name = "distributeurs")
public class DistributeursEntity extends PersonnesEntity {

    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "code")
    private String code ;
    @Column(name = "raisonsociale")
    private String raisonSociale;

    @OneToMany(mappedBy = "distributeursEntity" , fetch = FetchType.LAZY , cascade = {CascadeType.ALL})
    @Mapping("mouvements")
    private List<MouvementsEntity> mouvementEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "concerner",
    joinColumns = @JoinColumn(name = "precomouvementsqtes_id"),
    inverseJoinColumns = @JoinColumn(name = "distributeurs_id"))
    @Mapping("precomouvementsqtes")
    private List<PrecoMouvementsQtesEntity> precoMouvementsQtesEntities;

    @OneToMany(mappedBy = "distributeursEntity" )
    @JoinColumn(name = "promotions_id" , referencedColumnName = "id")
    @Mapping("promotions")
    private List<PromotionsEntity> promotionsEntities;
}
