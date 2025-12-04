package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"document"},ignoreUnknown = true)
@ToString(exclude = {"document"})
@EqualsAndHashCode(exclude = {"document"})
public class DocEtats {
    private String id;
    private int ordre;
    private Date dateCreation ;
    private Validations validationsEntity;
    private List<DocEtats> predecesseursDocEtat;
    private Etats etat;
    private Documents document;
}
