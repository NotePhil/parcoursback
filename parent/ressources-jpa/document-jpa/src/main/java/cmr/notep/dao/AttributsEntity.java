package cmr.notep.dao;

import cmr.notep.modele.TypeAttribut;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "attributs", schema = "document")
public class AttributsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
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
    @Enumerated(EnumType.STRING)
    @Column(name = "type_attribut", nullable = false)
    private TypeAttribut type_attribut;
    @Column(name = "valeurpardefaut")
    private String valeurParDefaut;
    @ManyToMany(mappedBy = "attributsEntities")
    private List<DocumentsEntity> documentsEntities;
    //@ManyToMany(mappedBy = "attributsEntities")
    //@Mapping("categories")
    //@JsonIgnore
   // private List<CategoriesEntity> categories ;

    @OneToMany(mappedBy = "attribut", fetch = FetchType.LAZY)
   // @Mapping("categories")
    private List<AssocierEntity> categoriesEntities;
}