package cmr.notep.repository;

import cmr.notep.dao.MouvementSoldeRessourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MouvementSoldeRessourcesRepository extends JpaRepository<MouvementSoldeRessourcesEntity, String> {
    List<MouvementSoldeRessourcesEntity> findByRessourcesEntity_Id(String ressourcesId);

    List<MouvementSoldeRessourcesEntity> findByStatut(String statut);

    List<MouvementSoldeRessourcesEntity> findByTypeTransaction(String typeTransaction);

    @Query("SELECT m FROM MouvementSoldeRessourcesEntity m " +
            "WHERE m.ressourcesEntity.id = :ressourcesId " +
            "AND m.dateCreation BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY m.dateCreation DESC")
    List<MouvementSoldeRessourcesEntity> findMovementsByPeriod(
            @Param("ressourcesId") String ressourcesId,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    @Query("SELECT COALESCE(SUM(m.quantiteMouvementee), 0) FROM MouvementSoldeRessourcesEntity m " +
            "WHERE m.ressourcesEntity.id = :ressourcesId AND m.validee = true AND m.typeTransaction = 'SORTIE'")
    Integer getTotalSorties(@Param("ressourcesId") String ressourcesId);

    @Query("SELECT COALESCE(SUM(m.quantiteMouvementee), 0) FROM MouvementSoldeRessourcesEntity m " +
            "WHERE m.ressourcesEntity.id = :ressourcesId AND m.validee = true AND m.typeTransaction = 'ENTREE'")
    Integer getTotalEntrees(@Param("ressourcesId") String ressourcesId);
}
