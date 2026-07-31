package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Une caractéristique est la valeur d'un attribut (référencé par son id) pour une ressource.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caracteristique {
    private String attributId;
    private String valeur;
}

