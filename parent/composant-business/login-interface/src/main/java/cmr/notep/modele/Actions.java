package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIgnoreProperties({"utilisateur","actionslangues"})
//@ToString(exclude = {"utilisateur","actionslangues"})
//@EqualsAndHashCode(exclude = {"utilisateur","actionslangues"})
public class Actions {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private String actionstatus;
    private ElementsBases elementsbase;
    private ActionsLangues actionslangues;
    private Utilisateurs utilisateur;
    private Historiques historique;
}