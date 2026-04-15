package cmr.notep.repository;

import cmr.notep.dao.FamillesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamillesRepository extends JpaRepository<FamillesEntity, String> {
    List<FamillesEntity> findByEtat(Boolean etat);

    List<FamillesEntity> findByLibelleContainingIgnoreCase(String libelle);

    @Query("SELECT f FROM FamillesEntity f WHERE f.etat = true ORDER BY f.libelle ASC")
    List<FamillesEntity> findActiveFamilies();
}
