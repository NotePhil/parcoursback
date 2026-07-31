package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"precoMouvementsQtes","promotions"})
@ToString(exclude = {"precoMouvementsQtes","promotions"})
@EqualsAndHashCode(exclude = {"precoMouvementsQtes","promotions"})
public class Ressources {
    private String id ;
    private Integer version;
    private String libelle;
    private String description;
    private Boolean etat;
    private Date dateCreation;
    private Date dateModification;
    private Integer quantite ;
    private Integer seuilAlerte;
    private Double prixEntree;
    private Double prixSortie;
    private String unite;
    private String scanBarCode;
    private List<PrecoMouvementsQtes> precoMouvementsQtes;
    private Familles famille;
    private List<Promotions> promotions;
    private List<Caracteristique> caracteristiques;
}
