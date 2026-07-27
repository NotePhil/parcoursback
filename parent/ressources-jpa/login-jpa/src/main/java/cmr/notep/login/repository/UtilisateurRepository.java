package cmr.notep.login.repository;

import cmr.notep.login.dao.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, String> {
    Optional<UtilisateurEntity> findByLogin(String login);
    Optional<UtilisateurEntity> findByLoginAndPassword(String login, String password);
}
