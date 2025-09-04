package cmr.notep.dao;

import cmr.notep.modele.TypeAttribut;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_attribut", nullable = false)
    private TypeAttribut type;
    @Column(name = "valeurpardefaut")
    private String valeursParDefaut;

    @ManyToMany(mappedBy = "attributsEntities")
    @Mapping("documents")
    private List<DocumentsEntity> documentsEntities;
    //@ManyToMany(mappedBy = "attributsEntities")
    //@Mapping("categories")
    //@JsonIgnore
   // private List<CategoriesEntity> categories ;

    @OneToMany(mappedBy = "attribut", fetch = FetchType.LAZY, orphanRemoval = true)
    @Mapping("categories")
    private List<AssocierEntity> categoriesEntities;
}