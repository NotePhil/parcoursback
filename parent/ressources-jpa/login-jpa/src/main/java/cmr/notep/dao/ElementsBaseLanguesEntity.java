package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "elementsbaselanques",schema = "login")
public class ElementsBaseLanguesEntity {

    @EmbeddedId
    private ElementsBaseLanguesEntityID id;

    @ManyToOne
    @MapsId("languesId")
    @JoinColumn(name = "langues_id" , referencedColumnName = "id")
    @Mapping("langue")
    private LanguesEntity languesEntity ;

    @ManyToOne
    @MapsId("elementsbaseId")
    @JoinColumn(name = "elementsbases_id" , referencedColumnName = "id")
    @Mapping("elementbases")
    private ElementsBasesEntity elementsBasesEntity ;

    @Column(name = "valeurlibelle")
    private String valeurLibelle ;
}
