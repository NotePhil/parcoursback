package cmr.notep.login.repository;

import cmr.notep.login.dao.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
}
