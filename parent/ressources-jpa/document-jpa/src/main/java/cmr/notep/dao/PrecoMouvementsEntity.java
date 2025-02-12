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
@Table(name = "precomouvements", schema = "document")
public class PrecoMouvementsEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name="libelle",nullable = false)
    private String libelle ;

    @Column(name = "etat")
    private boolean etat ;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;
    @Column(name="datemodification")
    private Date dateModification ;

    @Column(name="typemouvement")
    private String typeMouvement ;

    @ManyToMany(mappedBy = "precoMouvementsEntities")
    @Mapping("documents")
    private List<DocumentsEntity> documentsEntities ;

    @OneToMany(mappedBy = "precoMouvementsEntity" , fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("precoMouvementsQtes")
    private List<PrecoMouvementsQtesEntity> precoMouvementsQteEntities;
}
