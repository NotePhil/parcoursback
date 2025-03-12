package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Tickets {
    private String id;
    private String codecourt;
    private Date dateCreation;
    private Date dateModification;
    private List<TicketsFilesAttentes> ticketsfilesattentes;
}
