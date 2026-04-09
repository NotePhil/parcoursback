package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"ticketsfilesattentes"})
@EqualsAndHashCode(exclude = {"ticketsfilesattentes"})
@JsonIgnoreProperties({"ticketsfilesattentes"})
public class Tickets {
    private String id;
    private String idUnique;
    private Date dateCreation;
    private Date dateModification;
    private String statut;
    private List<TicketsFilesAttentes> ticketsFilesAttentes;
    private PersonnesPhysique personnesPhysique;
}
