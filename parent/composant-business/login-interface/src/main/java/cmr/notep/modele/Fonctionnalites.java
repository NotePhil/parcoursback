package cmr.notep.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"menus"})
@ToString(exclude = {"menus"})
@EqualsAndHashCode(exclude = {"menus"})
public class Fonctionnalites {
    private String id;
    private String fonction;
    private String icone;
    private boolean actif;
    private Menus menus;
    private List<Elements> elements;
}
