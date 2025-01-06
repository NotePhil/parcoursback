package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"famille"})
@ToString(exclude = {"famille"})
@EqualsAndHashCode(exclude = {"famille"})
public class Ressources {
    private String id ;
    private String libelle;
    private Boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private Integer quantite ;
    private Integer seuilAlerte;
    private Double prixEntree;
    private Double prixSortie;
    private String unites;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private Familles famille;
    private List<Mouvements> mouvements;
    private List<Promotions> promotions;
}
