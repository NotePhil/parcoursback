package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
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

    @Column(name = "datecreation", columnDefinition = "TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;
    @Column(name = "datemodification", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateModification;
    @Column(name = "etat")
    private Boolean etat ;

    @OneToMany(mappedBy = "filesAttentesEntity" , fetch = FetchType.LAZY )
    @Mapping("ticketsFilesAttentes")
    private List<TicketsFilesAttentesEntity> ticketsFilesAttentesEntities;

    @OneToOne
    @JoinColumn(name = "services_id" , referencedColumnName = "id")
    @Mapping("service")
    private ServicesEntity servicesEntity;
}
