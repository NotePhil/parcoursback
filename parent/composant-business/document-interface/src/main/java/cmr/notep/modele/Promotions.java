package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"documents"})
@ToString(exclude = {"documents"})
@EqualsAndHashCode(exclude = {"documents"})
public class Promotions {

    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateDebut;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date dateFin;
    private String codeUnique;
    private String typeRemise;
    private Double montantRemise;
    private Double pourcentageRemise;
    private Date dateCreation;
    private Date dateModification;
    private Distributeurs distributeur;
    private List<Ressources> ressources;
    private List <Familles> familles;
    private List<Documents> documents;
}
