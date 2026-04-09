package cmr.notep.repository;

import cmr.notep.dao.ValidationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValidationsRepository extends JpaRepository<ValidationsEntity, String> {
    List<ValidationsEntity> findByTypeValidation(String typeValidation);

    @Query("SELECT v FROM ValidationsEntity v WHERE v.roleEntity.id = :roleId")
    List<ValidationsEntity> findByRole(@Param("roleId") String roleId);
}
