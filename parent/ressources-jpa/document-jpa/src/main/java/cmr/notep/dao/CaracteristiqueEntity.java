package cmr.notep.dao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Représente une caractéristique : la valeur d'un attribut pour une ressource donnée.
 * Simple type valeur (pas d'entité autonome), stocké dans la table de liaison "caracteristiques".
 * L'intégrité référentielle sur attribut_id est garantie par une contrainte de clé étrangère en base.
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CaracteristiqueEntity {

    @Column(name = "attribut_id", nullable = false)
    private String attributId;

    @Column(name = "valeur", nullable = false)
    private String valeur;
}

