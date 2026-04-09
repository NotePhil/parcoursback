package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Personnes implements Serializable, IPersonnes {
    private String id ;
    private String adresse ;
    private String mail ;
    private  String telephone ;
    private  String qrcodevalue ;
    private Date dateCreation;
    private Date dateModification;
    private Set<IPersonnes> personnesRatachees = new HashSet<>();
}
