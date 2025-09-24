package cmr.notep.modele;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"document"})
@EqualsAndHashCode(exclude = {"document"})
@JsonIgnoreProperties(value = {"document"}, ignoreUnknown = true)
@Builder
public class DocEtats {
    private String id;
    private int ordre;
    private Date dateCreation ;
    private Date dateModification;
    private Validations validation;
    private List<DocEtats> predecesseurDocEtat = new ArrayList<>();
    private Etats etat;
    private Documents document;
    private  Etapes etape;
}
