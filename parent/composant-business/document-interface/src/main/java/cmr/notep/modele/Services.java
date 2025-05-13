package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"fileAttente"})
@ToString(exclude = {"fileAttente"})
@EqualsAndHashCode(exclude = {"fileAttente"})
public class Services {
    private String id ;
    private String description;
    private String libelle;
    private Boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private String codeUnique;
    private String localisation;
    private List<Missions> missions;
    private FilesAttentes fileAttente;
}
