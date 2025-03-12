package cmr.notep.modele;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class MacroPersonnes {

    private String type ;

    private Personnes personne;

    public MacroPersonnes(String type , Personnes personne) {
        this.type = type ;
        this.personne = personne ;
    }
}
