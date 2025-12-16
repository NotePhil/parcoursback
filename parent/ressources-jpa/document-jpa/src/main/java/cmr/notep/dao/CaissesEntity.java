package cmr.notep.dao;

import cmr.notep.modele.DetailsJson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="detailjson", columnDefinition = "jsonb")
    private DetailsJson detailjson ;

    //@OneToMany(mappedBy = "caissesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    //@Mapping("mouvementcaisses")
    //private List<MouvementCaissesEntity> mouvementCaissesEntities;
}
