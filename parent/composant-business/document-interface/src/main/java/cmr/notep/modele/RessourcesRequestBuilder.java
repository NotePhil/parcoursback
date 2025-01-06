package cmr.notep.modele;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RessourcesRequestBuilder {
    private String id;
    private String libelle;
    private Boolean etat;
    private Date[] periodeCreation; // [minDate, maxDate]
    private Date[] periodeModification; // [minDate, maxDate]
    private Integer quantite;
    private Integer seuilAlerte;
    private Double[] prixEntree; // [minPrice, maxPrice]
    private Double[] prixSortie; // [minPrice, maxPrice]
    private String unites;

    public String QueryBuilder()
    {
        StringBuilder query = new StringBuilder("SELECT * FROM ressources r WHERE 1=1 ");

        if (id != null && !id.isEmpty()) {
            query.append(" AND r.id LIKE '%").append(id).append("%'");
        }

        if (libelle != null && !libelle.isEmpty()) {
            query.append(" AND r.libelle LIKE '%").append(libelle).append("%'");
        }

        if (etat != null) {
            query.append(" AND r.etat = ").append(etat ? "true" : "false");
        }

        if (periodeCreation != null && periodeCreation.length == 2) {
            query.append(" AND r.datecreation BETWEEN '")
                    .append(new java.sql.Date(periodeCreation[0].getTime()))
                    .append("' AND '")
                    .append(new java.sql.Date(periodeCreation[1].getTime()))
                    .append("'");
        }

        if (periodeModification != null && periodeModification.length == 2) {
            query.append(" AND r.datemodification BETWEEN '")
                    .append(new java.sql.Date(periodeModification[0].getTime()))
                    .append("' AND '")
                    .append(new java.sql.Date(periodeModification[1].getTime()))
                    .append("'");
        }

        if (quantite != null) {
            query.append(" AND r.quantite = ").append(quantite);
        }

        if (seuilAlerte != null) {
            query.append(" AND r.seuilalerte = ").append(seuilAlerte);
        }

        if (prixEntree != null && prixEntree.length == 2) {
            query.append(" AND r.prixentree BETWEEN ")
                    .append(prixEntree[0])
                    .append(" AND ")
                    .append(prixEntree[1]);
        }

        if (prixSortie != null && prixSortie.length == 2) {
            query.append(" AND r.prixsortie BETWEEN ")
                    .append(prixSortie[0])
                    .append(" AND ")
                    .append(prixSortie[1]);
        }

        if (unites != null && !unites.isEmpty()) {
            query.append(" AND r.unite = '").append(unites).append("'");
        }

        return query.toString();
    }

}
