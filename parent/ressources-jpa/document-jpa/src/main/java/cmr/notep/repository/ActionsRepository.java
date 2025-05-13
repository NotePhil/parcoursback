package cmr.notep.repository;

import cmr.notep.dao.ActionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionsRepository extends JpaRepository<ActionsEntity,String> {
}
