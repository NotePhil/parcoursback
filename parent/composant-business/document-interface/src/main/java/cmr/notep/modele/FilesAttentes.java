package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;

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
    private List<TicketsFilesAttentes> ticketsfilesattentes;
    private Services service;
}
