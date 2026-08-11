package cmr.notep.util;

import java.util.List;

/**
 * Entité de test : l'attribut cible porte le même nom que l'attribut source ("lignes").
 */
public class DocumentEntitySameName {
    private List<LigneEntity> lignes;

    public List<LigneEntity> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneEntity> lignes) {
        this.lignes = lignes;
    }
}

