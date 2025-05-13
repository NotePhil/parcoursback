package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mouvementcaisses")
public class MouvementCaissesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "montant")
    private double montant ;

    @Column(name="moyenpaiement")
    private String moyenPaiement;

    @Column(name="referencepaiement")
    private String referencePaiement;

    @Column(name="detailjson")
    private String detailJSON;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name = "etat")
    private String etat;

    @Column(name = "typemvt")
    private String typeMvt;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caisses_id")
    @Mapping("caisse")
    private CaissesEntity caissesEntity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comptes_id")
    @Mapping("compte")
    private ComptesEntity comptesEntity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnels_id")
    @Mapping("personnel")
    private PersonnelsEntity personnelsEntity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplaires_id")
    @Mapping("exemplaire")
    private ExemplairesEntity exemplaireEntity ;
}
