package cmr.notep.repository;

import cmr.notep.dao.ServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServicesRepository extends JpaRepository<ServicesEntity,String> {
    Optional<ServicesEntity> findByCodeUnique(String codeUnique);

    List<ServicesEntity> findByEtat(Boolean etat);

    List<ServicesEntity> findByLibelleContainingIgnoreCase(String libelle);

    @Query("SELECT s FROM ServicesEntity s WHERE s.localisation = :localisation AND s.etat = true")
    List<ServicesEntity> findByLocalisation(@Param("localisation") String localisation);
}
