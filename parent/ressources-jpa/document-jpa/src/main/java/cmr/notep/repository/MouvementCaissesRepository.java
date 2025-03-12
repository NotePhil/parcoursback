package cmr.notep.repository;

import cmr.notep.dao.MouvementCaissesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementCaissesRepository extends JpaRepository<MouvementCaissesEntity, String> {
}
