package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "promotions", schema = "document")
public class PromotionsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "datedebut")
    private Date dateDebut;

    @Column(name = "datefin")
    private Date dateFin;

    @Column(name = "codeunique", unique = true)
    private String codeUnique;

    @Column(name = "typeremise")
    private String typeRemise;

    @Column(name = "valeurremise")
    private Double valeurRemise;

    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @ManyToOne
   // @PrimaryKeyJoinColumn(name = "distributeurs_id")
    @JoinColumn(name = "distributeurs_id")
    @Mapping("distributeur")
    private DistributeursEntity distributeursEntity ;

    @ManyToMany(mappedBy = "promotionsEntities" , fetch = FetchType.LAZY)
    @Mapping("ressources")
    private List<RessourcesEntity> ressourcesEntities;

    @ManyToMany
    @JoinTable(name = "famillespromotions" ,schema = "document",
            joinColumns = @JoinColumn(name = "promotions_id"),
            inverseJoinColumns = @JoinColumn(name = "familles_id"))
    @Mapping("familles")
    private List<FamillesEntity> famillesEntities;

    @ManyToMany
    @JoinTable(name = "documentspromotions" , schema = "document",
        joinColumns = @JoinColumn(name = "promotions_id"),
        inverseJoinColumns = @JoinColumn(name = "documents_id"))
    @Mapping("documents")
    private List<DocumentsEntity> documentsEntities;
}
