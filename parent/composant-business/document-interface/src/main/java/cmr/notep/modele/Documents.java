package cmr.notep.modele;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
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

    public List<Missions> missions ;

    private List<PrecoMouvements> precoMouvements ;

    private List<Attributs> attributs ;
    private List<Categories> categories ;
    private List<DocEtats> docEtats;
    private List<Promotions> promotions;
}
