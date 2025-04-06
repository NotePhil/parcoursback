package cmr.notep.repository;

import cmr.notep.dao.SessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<SessionsEntity,String> {
}
