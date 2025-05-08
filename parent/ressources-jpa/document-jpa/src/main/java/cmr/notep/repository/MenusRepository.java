package cmr.notep.repository;

import cmr.notep.dao.MenusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenusRepository extends JpaRepository<MenusEntity,String> {
}
