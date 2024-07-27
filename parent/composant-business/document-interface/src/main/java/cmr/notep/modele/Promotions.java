package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"distributeur","documents"})
@ToString(exclude = {"distributeur","documents"})
@EqualsAndHashCode(exclude = {"distributeur","documents"})
public class Promotions {
    private String id;
    private Date dateDebut;
    private Date dateFin;
    private String codeUnique;
    private String typeRemise;
    private Double valeurRemise;
    private Date dateCreation;
    private Date dateModification;
    private Distributeurs distributeur;
    private List<Ressources> ressources;
    private List <Familles> familles;
    private List<Documents> documents;
}
