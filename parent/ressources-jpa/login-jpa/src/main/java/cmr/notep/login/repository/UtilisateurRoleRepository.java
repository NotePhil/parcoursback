package cmr.notep.login.repository;

import cmr.notep.login.dao.UtilisateurRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRoleRepository extends JpaRepository<UtilisateurRoleEntity, String> {
    List<UtilisateurRoleEntity> findByUtilisateurId(String utilisateurId);
}
