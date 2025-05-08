package cmr.notep.dao;

import javax.persistence.Column;
import java.io.Serializable;

public class ElementLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "elements_id")
    private String elementsId ;
}
