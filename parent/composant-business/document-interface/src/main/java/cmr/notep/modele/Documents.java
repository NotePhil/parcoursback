package cmr.notep.modele;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = {"docEtats"})
@EqualsAndHashCode(exclude = {"docEtats"})
@JsonIgnoreProperties(value = {"docEtats"}, ignoreUnknown = true)
public class Documents {
    private String idDocument;

    private String titre;

    private String description;

    private boolean etat;

    private Date dateCreation;

    private Date dateModification;

    private TypeMouvement typeMouvement ;

    private Boolean afficherPrix ;

    private Boolean afficherUnite;

    private Boolean afficherDistributeur;

    private Boolean prixEditable;

    private Boolean contientRessources;

    private Boolean estencaissable;

    public List<Missions> missions ;

    private List<PrecoMouvements> precoMouvements ;

    private List<Attributs> attributs ;
    private List<Categories> categories ;
    private List<DocEtats> docEtats;
    private List<Promotions> promotions;
}
