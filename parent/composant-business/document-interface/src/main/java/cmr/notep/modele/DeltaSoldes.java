package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"exemplaire","compte"})
@ToString(exclude = {"exemplaire","compte"})
@EqualsAndHashCode(exclude = {"exemplaire","compte"})
public class DeltaSoldes {
    private String id;
    private double montantAvant ;
    private double montantApres ;
    private Date dateCreation ;
    private String typeMvt ;
    private Comptes compte;
    private Exemplaires exemplaire;
}
