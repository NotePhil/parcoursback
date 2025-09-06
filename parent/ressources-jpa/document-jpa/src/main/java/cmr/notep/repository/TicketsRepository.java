package cmr.notep.repository;

import cmr.notep.dao.TicketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketsRepository extends JpaRepository<TicketsEntity, String> {
}
