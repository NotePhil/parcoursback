package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"role"})
@EqualsAndHashCode(exclude = {"role"})
@JsonIgnoreProperties(value = {"role"}, ignoreUnknown = true)
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
