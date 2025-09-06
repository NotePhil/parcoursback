package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"personne"})
@EqualsAndHashCode(exclude = {"personne"})
@JsonIgnoreProperties({"personne"})
public class Tickets {
    private String id;
    private String codecourt;
    private Personnes personne;
    private String statut ;
    private Date dateCreation;
    private Date dateModification;
    private List<TicketsFilesAttentes> ticketsfilesattentes;
}
