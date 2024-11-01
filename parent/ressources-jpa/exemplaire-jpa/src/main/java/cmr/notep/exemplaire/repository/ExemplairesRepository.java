package cmr.notep.exemplaire.repository;

import cmr.notep.exemplaire.dao.ExemplairesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplairesRepository extends JpaRepository<ExemplairesEntity, String> {
}
