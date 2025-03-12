package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "elementslangues")
public class ElementLanguesEntity {

    @EmbeddedId
    private AssocierEntityID id;

    @ManyToOne
    @MapsId("languesId")
    @JoinColumn(name = "langues_id" , referencedColumnName = "id")
    @Mapping("langue")
    private LanguesEntity languesEntity ;

    @ManyToOne
    @MapsId("elementsId")
    @JoinColumn(name = "elements_id" , referencedColumnName = "id")
    @Mapping("element")
    private ElementsEntity elementsEntity ;

    @Column(name = "valeurlibelle")
    private String valeurLibelle ;

}
