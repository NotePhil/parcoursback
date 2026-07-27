package cmr.notep.login.modele;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private String id;
    private String titre;
    private String description;
    private Boolean etat;
    private Date dateCreation;
}
