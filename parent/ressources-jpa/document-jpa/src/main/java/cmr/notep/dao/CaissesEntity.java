package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "caisses")
public class CaissesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name="libelle",nullable = false)
    private String libelle ;

    @Column(name="solde",nullable = false)
    private double solde ;

    @Column(name="type",nullable = false)
    private String type ;

    @Column(name="detailjson",nullable = false)
    private String detailJSON ;

    @OneToMany(mappedBy = "caissesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("mouvementcaisses")
    private List<MouvementCaissesEntity> mouvementCaissesEntities;
}
