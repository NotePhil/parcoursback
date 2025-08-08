package cmr.notep.repository;

import cmr.notep.dao.AttributsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributsRepository extends JpaRepository<AttributsEntity, String> {

}
