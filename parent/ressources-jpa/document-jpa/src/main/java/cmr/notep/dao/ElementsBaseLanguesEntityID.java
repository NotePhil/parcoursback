package cmr.notep.dao;

import javax.persistence.Column;
import java.io.Serializable;

public class ElementsBaseLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "elementsbases")
    private String elementsbaseId;

}
