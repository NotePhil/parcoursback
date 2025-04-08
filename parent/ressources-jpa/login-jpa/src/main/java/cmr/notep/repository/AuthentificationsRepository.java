package cmr.notep.repository;

import cmr.notep.dao.AuthentificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthentificationsRepository extends JpaRepository<AuthentificationsEntity,String> {
}
