package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "ressources", schema = "document")
public class RessourcesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "description")
    private String description;
    @Column(name = "etat")
    private Boolean etat;
    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "seuilalerte")
    private Integer seuilAlerte;
    @Column(name = "prixentree")
    private Double prixEntree;
    @Column(name = "prixsortie")
    private Double prixSortie;
    @Column(name = "unite")
    private String unite ;
    @Column(name = "scanbarcode")
    private String scanBarCode ;

    @OneToMany(mappedBy = "ressourcesEntity",  fetch = FetchType.LAZY)
    @Mapping("precoMouvementsQtes")
    private List<PrecoMouvementsQtesEntity> precoMouvementsQtesEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familles_id")
    @Mapping("famille")
    private FamillesEntity famillesEntity;

    @ManyToMany
    @JoinTable(name = "ressourcespromotions" , schema = "document",
            joinColumns = @JoinColumn(name = "ressources_id"),
            inverseJoinColumns = @JoinColumn(name = "promotions_id"))
    @Mapping("promotions")
    private List<PromotionsEntity> promotionsEntities;

    @ElementCollection
    @CollectionTable(name = "caracteristiques", schema = "document",
            joinColumns = @JoinColumn(name = "ressources_id"))
    @Mapping("caracteristiques")
    private List<CaracteristiqueEntity> caracteristiquesEntities;

    /**
     * Ne pas modifier directement quantite.
     * Utiliser les services transactionnels pour les modifications.
     */
//    @Transient
//    @Mapping("solde")
//    public Integer getSolde() {
//        return this.quantite;
//    }
}
