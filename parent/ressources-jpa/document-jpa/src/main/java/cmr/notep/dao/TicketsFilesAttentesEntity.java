package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ticketsfilesattentes")
public class TicketsFilesAttentesEntity {

    @Id
    @Column(name = "id", nullable = false)
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
