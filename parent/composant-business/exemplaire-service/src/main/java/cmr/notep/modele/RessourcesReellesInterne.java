package cmr.notep.modele;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RessourcesReellesInterne {
    private String id;
    private Integer version;
    private Date dateCreation;
    private Date dateModification;
    private Integer quantite;
    private Integer seuilAlerte;
    private Double prixEntree;
    private Double prixSortie;
    private String unite;
    private String scanBarCode;
    private String ressourceId;
}

