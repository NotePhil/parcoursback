package cmr.notep.repository;

import cmr.notep.dao.DistributeursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistributeursRepository extends JpaRepository<DistributeursEntity, String>  {

    @Query(value = "SELECT p.id,p.comptes_id, p.datemodification,p.adresse,p.datecreation,p.mail,p.telephone,"+
            " p.qrcodevalue ,p.code, p.raisonsociale "+
            "FROM personnes p "+
            "WHERE p.raisonsociale LIKE %:value% AND p.person_type = 'distributeurs'", nativeQuery = true)
    List<DistributeursEntity> findByRaisonSociale(@Param("value") String value);

    @Query(value = "SELECT p.id,p.person_type,p.comptes_id, p.datemodification,p.adresse,p.datecreation,p.mail,p.telephone,"+
            " p.qrcodevalue ,p.code, p.raisonsociale "+
            "FROM personnes p "+
            "WHERE p.person_type = 'distributeurs' ", nativeQuery = true)
    List<DistributeursEntity> findAllDistributeurs();
}
