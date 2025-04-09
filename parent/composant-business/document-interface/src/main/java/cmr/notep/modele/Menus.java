package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"utilisateurs"})
@ToString(exclude = {"utilisateurs"})
@EqualsAndHashCode(exclude = {"utilisateurs"})
public class Menus {
    private String id;
    private boolean etat;
    private Date dateCreation;
    private Utilisateurs utilisateur;
    private Groupes groupe;
    private Elements elements;
}
