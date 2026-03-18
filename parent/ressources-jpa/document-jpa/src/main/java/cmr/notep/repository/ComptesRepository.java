package cmr.notep.repository;

import cmr.notep.dao.ComptesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComptesRepository extends JpaRepository<ComptesEntity, String> {
    List<ComptesEntity> findByEtat(Boolean etat);

    @Query("SELECT c FROM ComptesEntity c WHERE c.personnesEntity.id = :personneId")
    List<ComptesEntity> findByPersonne(@Param("personneId") String personneId);

    @Query("SELECT c FROM ComptesEntity c WHERE c.solde < :montantDecouvert")
    List<ComptesEntity> findComptesBelowThreshold(@Param("montantDecouvert") Integer montantDecouvert);
}
