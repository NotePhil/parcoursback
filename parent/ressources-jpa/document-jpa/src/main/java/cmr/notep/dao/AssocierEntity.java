package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "associer", schema = "document")
public class AssocierEntity {

    @EmbeddedId
    private AssocierEntityID id;

    @ManyToOne
    @MapsId("categoriesId")
    @JoinColumn(name = "categories_id", referencedColumnName = "id")
    @Mapping("categorie")
    private CategoriesEntity categorie ;

    @ManyToOne
    @MapsId("attributsId")
    @JoinColumn(name = "attributs_id", referencedColumnName = "id")
    @Mapping("attribut")
    private AttributsEntity attribut ;

    @Column(name="obligatoire")
    private boolean obligatoire ;

    @Column(name="ordre")
    private int ordre ;
}
