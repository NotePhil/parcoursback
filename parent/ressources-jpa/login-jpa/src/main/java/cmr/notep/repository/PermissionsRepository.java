package cmr.notep.repository;

import cmr.notep.dao.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<PermissionsEntity,String> {
}
