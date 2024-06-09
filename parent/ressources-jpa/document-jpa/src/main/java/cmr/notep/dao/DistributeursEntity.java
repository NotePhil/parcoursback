package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "distributeurs_id")
public class DistributeursEntity extends PersonnesEntity {

    @Column(name = "code")
    private String code ;

    @Column(name = "raisonsociale")
    private String raisonSociale;

    @OneToMany(mappedBy = "distributeursEntity" , fetch = FetchType.LAZY , cascade = {CascadeType.ALL})
    @Mapping("mouvements")
    private List<MouvementEntity> mouvementEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "livrer",
    joinColumns = @JoinColumn(name = "precomouvementsqtes_id"),
    inverseJoinColumns = @JoinColumn(name = "distributeurs_id"))
    @Mapping("precomouvementsqtes")
    private List<PrecoMouvementsQtesEntity> precoMouvementsQtesEntities;
}
