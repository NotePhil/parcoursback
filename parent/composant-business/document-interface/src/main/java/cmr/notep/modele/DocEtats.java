package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"document", "etape"})
@EqualsAndHashCode(exclude = {"document", "etape", "predecesseurDocEtat"})
@JsonIgnoreProperties(value = {"document", "etape"}, ignoreUnknown = true)
@Builder
public class DocEtats {
    private String id;
    private int ordre;
    private Date dateCreation ;
    private Date dateModification;
    private Validations validation;
    @JsonIgnoreProperties("predecesseurDocEtat")
    private Set<DocEtats> predecesseurDocEtat = new HashSet<>();
    private Etats etat;
    private Documents document;
    private  Etapes etape;
}
