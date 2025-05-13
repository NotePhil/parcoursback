package cmr.notep.repository;

import cmr.notep.dao.PersonnesPhysiquesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonnePhysiqueRepository extends JpaRepository<PersonnesPhysiquesEntity, String> {

    @Query(value = "SELECT p.id, p.nom, p.prenom, p.sexe, p.datenaissance, p.datemodification " +
            "FROM personnes_s p " +
            "WHERE (p.nom LIKE %:value% OR p.prenom LIKE %:value%) AND p.person_type = 'personnesphysique'", nativeQuery = true)
    List<PersonnesPhysiquesEntity> findByNomOrByPrenom(@Param("value")String value);

}
