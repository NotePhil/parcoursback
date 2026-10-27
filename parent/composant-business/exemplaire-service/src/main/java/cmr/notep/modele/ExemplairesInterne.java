package cmr.notep.modele;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ExemplairesInterne  {
    private String id;
    // Alias d'entree/sortie pour compatibilite avec les mappings issus du modele Documents.
    private String idDocument;
    private String code;
    private String codeBarre;
    private String intitule;
    private String documentId;
    //liste des id des exemplaires qui ont servi à la creation de cet exemplaire
    private List<String> idExemplairesParents;
    private Date dateCreation;
    private Date dateModification;
    private String personneBeneficiaireId;
    private String personneRattacheeId;
    private List<CorrespondantsInterne> personneEmettriceInterne;
    private List<OrdreEtatsInterne> ordreEtatsInternes;
    private List<MouvementsInterne> mouvementsInternes;
    private List<MouvementSoldeCaissesInterne> mouvementSoldeCaissesInternes;
    private List<CorrespondantsInterne> personnesDestinatairesInternes;
    private List<ExemplaireAttributsInterne> exemplaireAttributsInternes;

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
        this.documentId = idDocument;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
        this.idDocument = documentId;
    }
}
