package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "documents", schema = "document")
public class DocumentsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    @Mapping("idDocument")
    private String id;

    @Column(name = "titre", nullable = false)
    private String titre;

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

    @Column(name = "typemouvement")
    private String typeMouvement ;

    @Column(name = "formatcode")
    private String formatCode ;

    @Column(name = "afficherprix")
    private Boolean afficherPrix ;

    @Column(name = "afficherunite")
    private Boolean afficherUnite;

    @Column(name="afficherdistributeur")
    private Boolean afficherDistributeur;

    @Column(name="prixeditable")
    private Boolean prixEditable;

    @Column(name="contientressources")
    private Boolean contientRessources;

    @Column(name="estencaissable")
    private Boolean estencaissable;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "constituer", schema = "document",
            joinColumns = @JoinColumn(name = "documents_id"),
            inverseJoinColumns = @JoinColumn(name = "attributs_id"))
    @Mapping("attributs")
    private List<AttributsEntity> attributsEntities ;

    @OneToMany(mappedBy = "documentsEntity", fetch = FetchType.LAZY)
    @Mapping("categories")
    private List<CategoriesEntity> categoriesEntities;

    @ManyToMany(fetch = FetchType.LAZY  )
    @JoinTable(name = "traiter",schema = "document",
            joinColumns = @JoinColumn(name = "documents_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="missions_id", referencedColumnName = "id")
    )
    @Mapping("missions")
    private List<MissionsEntity> missionsEntities ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "suivre", schema = "document",
            joinColumns = @JoinColumn(name = "documents_id"),
            inverseJoinColumns = @JoinColumn(name = "precomouvements_id"))
    @Mapping("precoMouvements")
    private List<PrecoMouvementsEntity> precoMouvementsEntities ;

    @OneToMany(mappedBy = "documentsEntity" , fetch = FetchType.LAZY )
    @Mapping("docEtats")
    private List<DocEtatsEntity> docEtatsEntities;

    @ManyToMany(mappedBy = "documentsEntities")
    @Mapping("promotions")
    private List<PromotionsEntity> promotionsEntities;
}