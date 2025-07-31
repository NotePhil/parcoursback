package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"mission"}, ignoreUnknown = true)
@ToString(exclude = { "mission"})
@EqualsAndHashCode(exclude = { "mission"})
public class Remplir {
    private String id;
    private Role role;
    private Missions mission;
    private Date dateFin ;
    private Date dateCreation;
    private Date dateDebut;
    private boolean etat ;
    private boolean droitAjouter;
    private boolean droitModifier;
    private boolean droitConsulter;
    private boolean droitDeValider;
}
