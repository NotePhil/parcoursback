package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "caisses",schema = "document")
public class CaissesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name="libelle",nullable = false)
    private String libelle ;

    @Column(name="etat",nullable = false)
    private boolean etat ;

    @Column(name="solde",nullable = false)
    private double solde ;

    @Column(name="type",nullable = false)
    private String type ;

    @Column(name="detailsJson")
    private DetailsJson detailsJson ;

    //@OneToMany(mappedBy = "caissesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    //@Mapping("mouvementcaisses")
    //private List<MouvementCaissesEntity> mouvementCaissesEntities;
}

