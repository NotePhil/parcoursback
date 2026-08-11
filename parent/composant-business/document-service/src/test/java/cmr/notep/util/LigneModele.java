package cmr.notep.util;

/**
 * Modèle de test (top-level, public) utilisé par {@link BeanCopyUtilsTest} pour vérifier
 * {@link BeanCopyUtils#copyCollectionsWithDozer}. Doit rester top-level public pour que Dozer
 * puisse y accéder par réflexion (les classes imbriquées d'une classe de test non publique
 * ne sont pas accessibles de façon fiable via réflexion).
 */
public class LigneModele {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

