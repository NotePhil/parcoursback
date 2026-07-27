package cmr.notep.exemplaire.repository;


import cmr.notep.dao.MouvementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementRepository extends JpaRepository<MouvementsEntity, String> {
}
