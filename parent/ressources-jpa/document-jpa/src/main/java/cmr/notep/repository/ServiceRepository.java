package cmr.notep.repository;

import cmr.notep.dao.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServicesEntity,String> {
}