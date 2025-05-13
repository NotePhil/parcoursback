package cmr.notep.dao;

import javax.persistence.Column;
import java.io.Serializable;

public class ActionsLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "actions_id")
    private String actionsId ;
}
