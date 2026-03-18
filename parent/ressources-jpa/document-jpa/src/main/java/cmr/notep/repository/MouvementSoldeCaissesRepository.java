package cmr.notep.repository;

import cmr.notep.dao.MouvementSoldeCaissesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MouvementSoldeCaissesRepository extends JpaRepository<MouvementSoldeCaissesEntity, String> {
    List<MouvementSoldeCaissesEntity> findByCaissesEntity_Id(String caissesId);

    List<MouvementSoldeCaissesEntity> findByStatut(String statut);

    List<MouvementSoldeCaissesEntity> findByTypeTransaction(String typeTransaction);

    @Query("SELECT m FROM MouvementSoldeCaissesEntity m " +
            "WHERE m.caissesEntity.id = :caissesId " +
            "AND m.dateCreation BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY m.dateCreation DESC")
    List<MouvementSoldeCaissesEntity> findMovementsByPeriod(
            @Param("caissesId") String caissesId,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    @Query("SELECT COALESCE(SUM(m.soldeMovementee), 0.0) FROM MouvementSoldeCaissesEntity m " +
            "WHERE m.caissesEntity.id = :caissesId AND m.validee = true AND m.typeTransaction = 'DEBIT'")
    Double getTotalDebits(@Param("caissesId") String caissesId);

    @Query("SELECT COALESCE(SUM(m.soldeMovementee), 0.0) FROM MouvementSoldeCaissesEntity m " +
            "WHERE m.caissesEntity.id = :caissesId AND m.validee = true AND m.typeTransaction = 'CREDIT'")
    Double getTotalCredits(@Param("caissesId") String caissesId);
}
