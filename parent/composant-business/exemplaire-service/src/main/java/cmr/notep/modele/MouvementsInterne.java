package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"exemplaireInterne"})
@ToString(exclude = {"exemplaireInterne"})
@EqualsAndHashCode(exclude = {"exemplaireInterne"})
public class MouvementsInterne {
    private  String id ;
    private  String description ;
    private Integer quantiteInitiale;
    private Integer quantiteMouvementee;
    private Integer quantiteFinale;
    private double prix ;
    private String typeTransaction;
    private Boolean validee;
    private String valideeBy;
    private Date dateValidation;
    private String statut;
    private Date dateCreation ;
    private Date datePeremption ;
    private Date dateModification;
    private List<String> precoMouvementsRespecterIds;
    private List<String> precoMouvementsViolerIds;
    private RessourcesReellesInterne ressourceReelleInterne;
    private String distributeursId;
    private ExemplairesInterne exemplaireInterne;
}
