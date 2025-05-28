package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ElementsBaseLanguesEntityID implements Serializable {

    @Column(name = "langues_id")
    private String languesId ;

    @Column(name = "elementsbases")
    private String elementsbaseId;

}
