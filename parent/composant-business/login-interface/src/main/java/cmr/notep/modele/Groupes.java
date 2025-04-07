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
public class Groupes {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private List<Utilisateurs> utilisateurs;
    private Menus menus ;
}
