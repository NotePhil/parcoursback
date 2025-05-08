package cmr.notep.repository;

import cmr.notep.dao.ElementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementsRepository extends JpaRepository<ElementsEntity,String> {
}
