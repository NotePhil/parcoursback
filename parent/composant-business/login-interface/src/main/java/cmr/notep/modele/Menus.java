package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menus {
    private String id;
    private boolean etat;
    private Date dateCreation;
    private Utilisateurs utilisateur;
    private Groupes groupe;
    private Elements elements;
}
