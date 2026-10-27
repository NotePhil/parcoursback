package cmr.notep.modele;

public enum StatutMouvement {
    NOUVEAU("Nouveau"),
    EN_ATTENTE("En attente"),
    VALIDE("Valide"),
    REJETEE("Rejetée");

    private final String statutMvt;

    StatutMouvement(String statutMvt) {
        this.statutMvt = statutMvt;
    }

    public String getStatutMvt() {
        return statutMvt;
    }
}
