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
