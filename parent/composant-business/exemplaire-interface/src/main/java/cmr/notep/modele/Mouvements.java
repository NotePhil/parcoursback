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
    private List<PrecoMouvements> precoMouvementsRespecter;
    private List<PrecoMouvements> precoMouvementsVioler;
    private RessourcesReelles ressourceReelle;
    private Distributeurs distributeur;
    private Exemplaires exemplaire;
}
