package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories", schema = "document")
public class CategoriesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "ordre")
    private String ordre;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;

   /* @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "associer",
            joinColumns = @JoinColumn(name = "id_category"),
            inverseJoinColumns = @JoinColumn(name = "id_attribut"))
    @Mapping("attributs")
    private List<AttributsEntity> attributsEntities , cascade = {CascadeType.PERSIST, CascadeType.MERGE} ;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documents_id")
    @Mapping("document")
    private DocumentsEntity documentsEntity ;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    @Mapping("attributs")
    private List<AssocierEntity> attributsEntities;
}
