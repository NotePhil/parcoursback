package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"fileAttente","ticket"})
@ToString(exclude = {"fileAttente","ticket"})
@EqualsAndHashCode(exclude = {"fileAttente","ticket"})
public class TicketsFilesAttentes {
    private String id;
    private Boolean etat ;
    private Date dateAffectation;
    private Tickets ticket;
    private FilesAttentes fileAttente;
}
