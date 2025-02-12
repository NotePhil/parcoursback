package cmr.notep.exemplaire.repository;


import cmr.notep.exemplaire.dao.MouvementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementRepository extends JpaRepository<MouvementsEntity, String> {
}
