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
    private String unites ;

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
