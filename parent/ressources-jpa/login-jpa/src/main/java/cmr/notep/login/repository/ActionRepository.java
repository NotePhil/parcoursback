package cmr.notep.login.repository;

import cmr.notep.login.dao.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionRepository extends JpaRepository<ActionEntity, String> {
    List<ActionEntity> findByElementIdOrderByOrdreAsc(String elementId);
    List<ActionEntity> findByElementId(String elementId);
    int countByElementId(String elementId);

    @Modifying
    @Query("UPDATE ActionEntity a SET a.ordre = :ordre WHERE a.id = :id")
    void updateOrdre(@Param("id") String id, @Param("ordre") int ordre);
}
