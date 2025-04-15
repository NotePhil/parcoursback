package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Personnes {
    private String id ;
    private String adresse ;
    private String mail ;
    private  String telephone ;
    private  String qrcodevalue ;
    private List<Personnes> personnesRatachees = new ArrayList<>();
    private Comptes compte;
    private Exemplaires exemplaires;


//    private List<Promotions> promotions;
//    private Mouvements mouvements;
//    private List<PrecoMouvementsQtes> precomouvementsqtes;
}
