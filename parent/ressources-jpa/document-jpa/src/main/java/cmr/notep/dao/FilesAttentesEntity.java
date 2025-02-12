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
@Table(name = "filesattentes", schema = "document")
public class FilesAttentesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "datecreation", updatable = false)
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "etat")
    private Boolean etat ;

    @OneToMany(mappedBy = "filesAttentesEntity" , fetch = FetchType.LAZY , cascade = {CascadeType.ALL})
    @Mapping("ticketsfilesattentes")
    private List<TicketsFilesAttentesEntity> ticketsFilesAttentesEntities;

    @OneToOne
    @JoinColumn(name = "services_id" , referencedColumnName = "id")
    @Mapping("service")
    private ServicesEntity servicesEntity;
}
