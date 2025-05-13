package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"validation","etape","etat","document"})
@ToString(exclude = {"validation","etape","etat","document"})
@EqualsAndHashCode(exclude = {"validation","etape","etat","document"})
public class DocEtats {
    private String id;
    private int ordre;
    private Date dateCreation ;
    private Date dateModification;
    private Validations validation;
    private List<DocEtats> predecesseurDocEtat;
    private Etats etat;
    private Documents document;
    private Etapes etape;
}
