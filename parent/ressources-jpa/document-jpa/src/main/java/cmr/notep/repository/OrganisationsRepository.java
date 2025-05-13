package cmr.notep.repository;

import cmr.notep.dao.OrganisationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationsRepository extends JpaRepository<OrganisationsEntity,String> {
}
