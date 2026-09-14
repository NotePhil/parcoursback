package cmr.notep.repository;

import cmr.notep.dao.JouerRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JouerRolesRepository extends JpaRepository<JouerRolesEntity, String> {
    void deleteByPersonnelsEntityId(String personnelsId);
    List<JouerRolesEntity> findByPersonnelsEntityId(String personnelsId);
}
