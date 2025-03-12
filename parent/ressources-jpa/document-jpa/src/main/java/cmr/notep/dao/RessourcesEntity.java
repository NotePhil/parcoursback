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
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "quantite")
    private Integer quantite ;
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
}
