package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
public class Personnes implements Serializable, IPersonnes {
    private String id ;
    private String adresse ;
    private String mail ;
    private String person_type ;
    private  String telephone ;
    private  String qrcodevalue ;
    private Comptes compte;
    private Exemplaires exemplaires;
    private Date dateCreation;
    private Date dateModification;
    private List<IPersonnes> personnesRatachees = new ArrayList<>();
}
