package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"role"})
@ToString(exclude = {"role"})
@EqualsAndHashCode(exclude = {"role"})
public class Validations {
    private String id;
    private String code ;
    private String etat ;
    private Date dateCreation;
    private Roles role;
    private List<DocEtats> docetats;
}
