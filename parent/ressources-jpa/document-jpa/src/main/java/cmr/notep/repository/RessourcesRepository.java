package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourcesRepository extends JpaRepository<RessourcesEntity, String>, RessourcesRepositoryCustom {
}