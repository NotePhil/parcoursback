package cmr.notep.repository;

import cmr.notep.dao.UtilisateursEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateursRepository extends JpaRepository<UtilisateursEntity,String> {
}
