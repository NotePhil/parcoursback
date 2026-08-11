package cmr.notep.util;

import java.util.List;

/**
 * Entité de test : l'attribut cible suit la convention "nom + Entities" ("lignesEntities").
 */
public class DocumentEntityPlural {
    private List<LigneEntity> lignesEntities;

    public List<LigneEntity> getLignesEntities() {
        return lignesEntities;
    }

    public void setLignesEntities(List<LigneEntity> lignesEntities) {
        this.lignesEntities = lignesEntities;
    }
}

