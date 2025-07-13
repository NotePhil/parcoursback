package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"utilisateurs","menus"})
@ToString(exclude = {"utilisateurs","menus"})
@EqualsAndHashCode(exclude = {"utilisateurs","menus"})
public class Groupes {
    private String id;
    private String libelle;
    private boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private List<Utilisateurs> utilisateurs;
    private List<Menus> menus ;
}
