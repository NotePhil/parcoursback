package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Validations {
    private String id;
    private String libelle ;
    private String code ;
    private String etat ;
    private Date dateCreation;
    private Roles role;
    private Date dateModification;
    private String typeVote ;
    private int dureeVote ;
    private String typeValidation ;
    private List<DocEtats> docetats;
}
