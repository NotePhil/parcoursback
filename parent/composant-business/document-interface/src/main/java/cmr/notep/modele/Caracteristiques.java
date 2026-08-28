package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Une caractéristique est la valeur d'un attribut pour une ressource.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Caracteristiques {
    private Attributs attributs;
    private String valeur;
}

