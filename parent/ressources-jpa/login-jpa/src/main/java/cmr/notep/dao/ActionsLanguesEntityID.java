package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ActionsLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "actions_id")
    private String actionsId ;
}
