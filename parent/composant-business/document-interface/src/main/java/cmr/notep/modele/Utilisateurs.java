package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utilisateurs {
    private String id;
    private String login;
    private String mdp;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private Groupes groupe;
    private List<Organisations> organisations;
    private Menus menus;
}
