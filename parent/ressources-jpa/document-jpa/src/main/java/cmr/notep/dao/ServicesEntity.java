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
    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "codeunique", nullable = false , unique = true)
    private String codeUnique;
    @OneToMany(mappedBy = "servicesEntity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Mapping("missions")
    private List<MissionsEntity> missionsEntities ;

    @OneToOne
    @JoinColumn(name = "filesattentes_id" , referencedColumnName = "id")
    @Mapping("fileAttente")
    private FilesAttentesEntity filesAttentesEntity;
}
