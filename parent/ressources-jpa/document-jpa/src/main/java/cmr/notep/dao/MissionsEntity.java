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
@Table(name = "missions", schema = "document")
public class MissionsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name="libelle",nullable = false)
    private String libelle ;

    @Column(name="description")
    private String description;

    @Column(name = "etat")
    private boolean etat ;

    @Column(name = "datecreation", updatable = false,nullable = false)
    private Date dateCreation ;

    @Column(name="datemodification")
    private Date dateModification ;

    @ManyToMany(fetch = FetchType.LAZY , cascade = {CascadeType.ALL} )
    @JoinTable(name = "traiter",schema = "document",
        joinColumns = @JoinColumn(name = "missions_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="documents_id", referencedColumnName = "id")
    )
    @Mapping("documents")
    private List<DocumentsEntity> documentsEntities ;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "services_id")
    @Mapping("service")
    private ServicesEntity servicesEntity ;

    @OneToMany(mappedBy = "missionsEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("roles")
    List<RemplirEntity> remplirEntities;
}
