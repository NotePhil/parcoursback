package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ticketsfilesattentes", schema = "document")
public class TicketsFilesAttentesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "UUID")
    private String id;

    @Column(name = "etat")
    private Boolean etat ;

    @Column(name = "dateaffectation")
    private Date dateAffectation;

    @ManyToOne
    @JoinColumn(name = "tickets_id")
    @Mapping("ticket")
    private TicketsEntity ticketsEntity;

    @ManyToOne
    @JoinColumn(name = "filesattentes_id")
    @Mapping("fileAttente")
    private FilesAttentesEntity filesAttentesEntity;
}
