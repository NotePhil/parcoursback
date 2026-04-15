package cmr.notep.repository;

import cmr.notep.dao.CaissesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaissesRepository extends JpaRepository<CaissesEntity, String> {
    List<CaissesEntity> findByEtat(Boolean etat);

    List<CaissesEntity> findByType(String type);

    List<CaissesEntity> findByLibelleContainingIgnoreCase(String libelle);

    @Query("SELECT c FROM CaissesEntity c WHERE c.solde > :montantMin ORDER BY c.solde DESC")
    List<CaissesEntity> findCaissesByMinimumBalance(@Param("montantMin") Double montantMin);
}
