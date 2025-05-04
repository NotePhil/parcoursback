package cmr.notep.repository;

import cmr.notep.dao.UtilisateursEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateursRepository extends JpaRepository<UtilisateursEntity,String> {
    Optional<UtilisateursEntity> findByLogin(String username);
}
