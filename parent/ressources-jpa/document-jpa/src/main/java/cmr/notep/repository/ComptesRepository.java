package cmr.notep.repository;

import cmr.notep.dao.ComptesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComptesRepository extends JpaRepository<ComptesEntity, String> {
}
