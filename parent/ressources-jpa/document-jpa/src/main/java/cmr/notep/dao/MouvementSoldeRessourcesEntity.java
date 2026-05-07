package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité pour tracer les mouvements de solde des ressources
 * Permet un audit complet des modifications sans permettre de modifier directement le solde
 */
@Getter
@Setter
@Entity
@Table(name = "mouvements_solde_ressources", schema = "document")
public class MouvementSoldeRessourcesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressources_id", nullable = false)
    @Mapping("ressource")
    private RessourcesEntity ressourcesEntity;

    @Column(name = "quantite_initiale")
    private Integer quantiteInitiale;

    @Column(name = "quantite_mouvementee")
    private Integer quantiteMouvementee;

    @Column(name = "quantite_finale")
    private Integer quantiteFinale;

    @Column(name = "type_transaction")
    private String typeTransaction; // ENTREE, SORTIE, AJUSTEMENT

    @Column(name = "description")
    private String description;

    @Column(name = "motif")
    private String motif;

    @Column(name = "code_reference")
    private String codeReference;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;

    @Column(name = "validee")
    private Boolean validee = false;

    @Column(name = "validee_by")
    private String valideeBy;

    @Column(name = "date_validation")
    private Date dateValidation;

    @Column(name = "statut")
    private String statut; // EN_ATTENTE, VALIDEE, REJETEE
}
