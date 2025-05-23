package cmr.notep.dao;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssocierEntityID implements Serializable {

    private String attributsId ;

    private String categoriesId ;

}
