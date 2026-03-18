package cmr.notep.repository;

import cmr.notep.dao.PersonnelsEntity;
import cmr.notep.dao.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolesRepository extends JpaRepository<RolesEntity, String> {
    List<RolesEntity> findByEtat(Boolean etat);

    List<RolesEntity> findByTitreContainingIgnoreCase(String titre);

    @Query("SELECT r FROM RolesEntity r WHERE r.etat = true ORDER BY r.titre ASC")
    List<RolesEntity> findActiveRoles();
}
