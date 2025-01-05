package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourcesRepository extends JpaRepository<RessourcesEntity, String> {
}