package cmr.notep.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dozer.Mapping;

import java.util.Objects;

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
public class CaracteristiquesEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribut_id", nullable = false)
    @Mapping("attributs")
    private AttributsEntity attributsEntity;

    @Column(name = "valeur", nullable = false)
    private String valeur;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaracteristiquesEntity that)) return false;
        return Objects.equals(getAttributId(this.attributsEntity), getAttributId(that.attributsEntity))
                && Objects.equals(valeur, that.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributId(attributsEntity), valeur);
    }

    private static Object getAttributId(AttributsEntity attributsEntity) {
        return attributsEntity == null ? null : attributsEntity.getId(); // adapte getId() au vrai nom du PK
    }
}

