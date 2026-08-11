package cmr.notep.util;

import org.dozer.Mapping;

import java.util.List;

/**
 * Entité de test : l'attribut cible est résolu via l'annotation Dozer {@code @Mapping("lignes")}.
 */
public class DocumentEntityAnnotated {
    @Mapping("lignes")
    private List<LigneEntity> details;

    public List<LigneEntity> getDetails() {
        return details;
    }

    public void setDetails(List<LigneEntity> details) {
        this.details = details;
    }
}

