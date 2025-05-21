package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ElementLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "elements_id")
    private String elementsId ;

}
