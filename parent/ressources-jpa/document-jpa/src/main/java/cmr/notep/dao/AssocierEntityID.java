package cmr.notep.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class AssocierEntityID implements Serializable {
    @Column(name = "attributs_id")
    private String attributsId ;

    @Column(name = "categories_id")
    private String categoriesId ;

}
