package cmr.notep.login.repository;

import cmr.notep.login.dao.FonctionnaliteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FonctionnaliteRepository extends JpaRepository<FonctionnaliteEntity, String> {
    List<FonctionnaliteEntity> findByGroupeIdOrderByOrdreAsc(String groupeId);
    List<FonctionnaliteEntity> findByGroupeId(String groupeId);
    int countByGroupeId(String groupeId);

    @Modifying
    @Query("UPDATE FonctionnaliteEntity f SET f.ordre = :ordre WHERE f.id = :id")
    void updateOrdre(@Param("id") String id, @Param("ordre") int ordre);
}
