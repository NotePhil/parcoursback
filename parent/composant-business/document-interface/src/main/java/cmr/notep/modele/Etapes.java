package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etapes {
    private String id;
    private String libelle;
    private String etat;
    private Date dateModification;
    private Parcours parcour;
    private List<DocEtats> docEtats;
}
