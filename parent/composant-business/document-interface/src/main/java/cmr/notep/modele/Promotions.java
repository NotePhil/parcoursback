package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"documents","distributeur"})
@ToString(exclude = {"documents","distributeur"})
@EqualsAndHashCode(exclude = {"documents","distributeur"})
public class Promotions {

    private String id;
    private Date dateDebut;
    private Date dateFin;
    private String codeUnique;
    private String typeRemise;
    private Double valeurRemise;
    private Date dateCreation;
    private Distributeurs distributeur;
    private List<Ressources> ressources;
    private List <Familles> familles;
    private List<Documents> documents;
}
