package cmr.notep.util;

/**
 * Entité de test : aucun attribut ne correspond à la collection "lignes" du modèle source,
 * utilisée pour vérifier que {@link BeanCollectionMappingException} est bien levée.
 */
public class DocumentEntityNoMatch {
    private String other;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}

