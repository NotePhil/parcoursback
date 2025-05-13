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
public class FilesAttentes {
    private String id;
    private Date dateCreation;
    private Date dateModification;
    private Boolean etat ;
    private Services service;
    private List<TicketsFilesAttentes> ticketsfilesattentes;
}
