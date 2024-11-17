package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public  class Personnes {
    private String id ;
    private String adresse ;
    private String mail ;
    private  String telephone ;
    private  String qrcodevalue ;
    private Date dateCreation;
    private Date dateModification;
    private List<Personnes> personnesRatachees;
    private Comptes compte;
    private List<Exemplaires> exemplaires;
    private String person_type ;
}