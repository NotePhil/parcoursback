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
public class Parcours {
    private UUID id;
    private String libelle;
    private Date dateCreation;
    private Date dateModification;
    private List<Etapes> etapes;
}
