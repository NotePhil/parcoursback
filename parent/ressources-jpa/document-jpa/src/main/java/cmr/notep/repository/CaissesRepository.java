package cmr.notep.repository;

import cmr.notep.dao.CaissesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaissesRepository extends JpaRepository<CaissesEntity, String> {
}
