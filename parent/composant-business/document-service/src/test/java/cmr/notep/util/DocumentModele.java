package cmr.notep.util;

import java.util.List;

/**
 * Modèle de test (top-level, public) représentant un "document" possédant une collection de lignes.
 */
public class DocumentModele {
    private List<LigneModele> lignes;

    public List<LigneModele> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneModele> lignes) {
        this.lignes = lignes;
    }
}

