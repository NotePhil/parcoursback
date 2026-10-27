package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mouvements", schema = "exemplaire")
public class MouvementsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "description" , nullable = false)
    private  String description ;

    @Column(name = "quantite_initiale")
    private Integer quantiteInitiale;

    @Column(name = "quantite_mouvementee")
    private Integer quantiteMouvementee;

    @Column(name = "quantite_finale")
    private Integer quantiteFinale;

    @Column(name = "prix")
    private double prix ;

    @Column(name = "type_transaction")
    private String typeTransaction; // ENTREE, SORTIE, AJUSTEMENT


    @Column(name = "validee")
    private Boolean validee = false;

    @Column(name = "validee_by")
    private String valideeBy;

    @Column(name = "date_validation")
    private Date dateValidation;

    @Column(name = "statut")
    private String statut; // EN_ATTENTE, VALIDEE, REJETEE

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name = "dateperemption")
    private Date datePeremption ;

    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;

    @ElementCollection
    @CollectionTable(name = "respecterprecomouvement", joinColumns = @JoinColumn(name = "mouvements_id"), schema = "exemplaire")
    @Column(name = "precomouvements_id")
    private List<String> precoMouvementsRespecterIds;

    @ElementCollection
    @CollectionTable(name = "violerprecomouvement", joinColumns = @JoinColumn(name = "mouvements_id"), schema = "exemplaire")
    @Column(name = "precomouvements_id")
    private List<String> precoMouvementsViolerIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressourcesreelles_id")
    @Mapping("ressourceReelleInterne")
    private RessourcesReellesEntity ressourceReelleEntity;

    @Column(name = "distributeurs_id")
    private String distributeursId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplaires_id")
    @Mapping("exemplaireInterne")
    ExemplairesEntity exemplaireEntity;
}
