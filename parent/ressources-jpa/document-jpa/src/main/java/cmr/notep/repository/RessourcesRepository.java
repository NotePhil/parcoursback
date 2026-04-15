package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RessourcesRepository extends JpaRepository<RessourcesEntity, String> {
    List<RessourcesEntity> findByFamillesEntity_Id(String famillesId);

    List<RessourcesEntity> findByEtat(Boolean etat);

    List<RessourcesEntity> findByLibelleContainingIgnoreCase(String libelle);

    @Query("SELECT r FROM RessourcesEntity r WHERE r.quantite < r.seuilAlerte AND r.etat = true")
    List<RessourcesEntity> findResourcesBelowThreshold();

    @Query("SELECT r FROM RessourcesEntity r " +
            "WHERE r.quantite < :quantiteMin " +
            "ORDER BY r.quantite ASC")
    List<RessourcesEntity> findResourcesWithLowStock(@Param("quantiteMin") Integer quantiteMin);
}
