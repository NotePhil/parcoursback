package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.dozer.Mapping;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"filesAttente"})
@ToString(exclude = {"filesAttente"})
@EqualsAndHashCode(exclude = {"filesAttente"})
public class TicketsFilesAttentes {
    private String id;
    private Boolean etat ;
    private Date dateAffectation;
    private Tickets ticket;
    private FilesAttentes filesAttente;
}
