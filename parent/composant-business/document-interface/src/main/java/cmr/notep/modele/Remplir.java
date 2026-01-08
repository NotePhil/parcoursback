package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"role"}, ignoreUnknown = true)
@ToString(exclude = { "role"})
@EqualsAndHashCode(exclude = { "role"})
public class Remplir {
    private String id;
    private Roles role;
    private Missions mission;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateFin ;
    private Date dateCreation;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateDebut;
    private boolean etat ;
    private boolean droitAjouter;
    private boolean droitModifier;
    private boolean droitConsulter;
    private boolean droitDeValider;
}
