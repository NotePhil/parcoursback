package cmr.notep.repository;

import cmr.notep.dao.HistoriquesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriquesRepository extends JpaRepository<HistoriquesEntity,String> {
}
