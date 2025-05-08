package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "actionslangues")
public class ActionsLanguesEntity {

    @EmbeddedId
    private AssocierEntityID id;

    @ManyToOne
    @MapsId("languesId")
    @JoinColumn(name = "langues_id" , referencedColumnName = "id")
    @Mapping("langue")
    private LanguesEntity languesEntity ;

    @ManyToOne
    @MapsId("actionsId")
    @JoinColumn(name = "actions_id" , referencedColumnName = "id")
    @Mapping("action")
    private ActionsEntity actionsEntity ;

    @Column(name = "valeurlibelle")
    private String valeurLibelle ;
}
