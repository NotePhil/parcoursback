package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"ressource","distributeur","precoMouvementsRespecter","precoMouvementsVioler"})
@ToString(exclude = {"ressource","distributeur","precoMouvementsRespecter","precoMouvementsVioler"})
@EqualsAndHashCode(exclude = {"ressource","distributeur","precoMouvementsRespecter","precoMouvementsVioler"})
public class Mouvements {
    private String id ;
    private  String description ;
    private int quantite ;
    private double prix ;
    private Date dateCreation ;
    private Date datePeremption ;
    private Date dateModification;
    private List<PrecoMouvements> precoMouvementsRespecter;
    private List<PrecoMouvements> precoMouvementsVioler;
    private Ressources ressource;
    private Distributeurs distributeur;

}
