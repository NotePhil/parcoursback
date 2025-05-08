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
@Table(name = "comptes")
public class ComptesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name="solde",nullable = false)
    private double solde ;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name="etat",nullable = false)
    private String etat ;

    @Column(name = "montantdecouvertmax")
    private double montantDecouvertMax ;

    @Column(name="libelle",nullable = false)
    private String libelle ;

    @OneToOne
    @JoinColumn(name = "personnes_id" , referencedColumnName = "id")
    @Mapping("personne")
    private PersonnesEntity personnesEntity;

    @OneToMany(mappedBy = "comptesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("mouvementcaisses")
    private List<MouvementCaissesEntity> mouvementCaissesEntities;

    @OneToMany(mappedBy = "comptesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("deltasoldes")
    private List<DeltaSoldesEntity> deltaSoldeEntities;
}
