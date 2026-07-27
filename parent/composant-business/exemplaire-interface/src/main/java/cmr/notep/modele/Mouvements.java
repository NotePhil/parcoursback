package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"exemplaire"})
@ToString(exclude = {"exemplaire"})
@EqualsAndHashCode(exclude = {"exemplaire"})
public class Mouvements {
    private  String id ;
    private  String description ;
    private int quantite ;
    private double prix ;
    private Date dateCreation ;
    private Date datePeremption ;
    private List<PrecoMouvements> precoMouvementsRespecter;
    private List<PrecoMouvements> precoMouvementsVioler;
    private Ressources ressource;
    private Distributeurs distributeur;
    private Exemplaires exemplaire;
}
