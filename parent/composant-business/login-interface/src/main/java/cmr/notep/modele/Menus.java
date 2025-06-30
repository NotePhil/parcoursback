package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"utilisateur","elements"})
@ToString(exclude = {"utilisateur","elements"})
@EqualsAndHashCode(exclude = {"utilisateur","elements"})
public class Menus {
    private String id;
    private boolean etat;
    private String langue;
    private Date dateCreation;
    private Utilisateurs utilisateur;
    private Groupes groupe;
    private List<Elements> elements;
    private List<Fonctionnalites> fonctionnalites;
}
