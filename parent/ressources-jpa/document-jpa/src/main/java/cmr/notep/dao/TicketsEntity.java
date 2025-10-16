package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tickets", schema = "document")
public class TicketsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "idunique")
    private String idUnique;
    @Column(name = "datecreation", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreation;
    @Column(name = "datemodification")
    private Date dateModification;
    @Column(name = "statut")
    private String statut;

    @OneToMany(mappedBy = "ticketsEntity" , fetch = FetchType.LAZY )
    @Mapping("ticketsfilesattentes")
    private List<TicketsFilesAttentesEntity> ticketsFilesAttentesEntities;
    @ManyToOne
    @JoinColumn(name = "personnesphysique_id")
    private PersonnesPhysiquesEntity personnesPhysique;
}
