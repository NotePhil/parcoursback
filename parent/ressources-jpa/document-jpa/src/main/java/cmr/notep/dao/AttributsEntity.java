package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "valeurpardefaut")
    private String valeurParDefaut;
    @ManyToMany(mappedBy = "attributsEntities")
    private List<DocumentsEntity> documentsEntities;
    //@ManyToMany(mappedBy = "attributsEntities")
    //@Mapping("categories")
    //@JsonIgnore
   // private List<CategoriesEntity> categories ;

    @OneToMany(mappedBy = "attribut", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    //@Mapping("categories")
    private List<AssocierEntity> categoriesEntities;
}