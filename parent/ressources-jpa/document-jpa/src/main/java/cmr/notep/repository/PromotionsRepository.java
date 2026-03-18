package cmr.notep.repository;

import cmr.notep.dao.PromotionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PromotionsRepository extends JpaRepository<PromotionsEntity, String> {
    Optional<PromotionsEntity> findByCodeUnique(String codeUnique);

    @Query("SELECT p FROM PromotionsEntity p " +
            "WHERE p.dateDebut <= :date AND p.dateFin >= :date")
    List<PromotionsEntity> findActivePromotions(@Param("date") Date date);

    @Query("SELECT p FROM PromotionsEntity p " +
            "WHERE p.distributeursEntity.id = :distributeurId")
    List<PromotionsEntity> findByDistributeur(@Param("distributeurId") String distributeurId);
}
