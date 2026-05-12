package cmr.notep.dao;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "services", schema = "document")
public class ServicesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "localisation")
    private String localisation;
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
    @Column(name = "codeunique", nullable = false , unique = true)
    @ColumnTransformer(write = "UPPER(?)")
    private String codeUnique;
    @OneToMany(mappedBy = "servicesEntity", fetch = FetchType.LAZY)
    @Mapping("missions")
    private List<MissionsEntity> missionsEntities ;

    @OneToOne
    @JoinColumn(name = "filesattentes_id" , referencedColumnName = "id")
    @Mapping("fileAttente")
    private FilesAttentesEntity filesAttentesEntity;
}
