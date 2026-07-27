package cmr.notep.login.repository;

import cmr.notep.login.dao.ElementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ElementRepository extends JpaRepository<ElementEntity, String> {
    List<ElementEntity> findByFonctionnaliteIdOrderByOrdreAsc(String fonctionnaliteId);
    List<ElementEntity> findByFonctionnaliteId(String fonctionnaliteId);
    int countByFonctionnaliteId(String fonctionnaliteId);

    @Modifying
    @Query("UPDATE ElementEntity e SET e.ordre = :ordre WHERE e.id = :id")
    void updateOrdre(@Param("id") String id, @Param("ordre") int ordre);
}
