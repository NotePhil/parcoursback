package cmr.notep.repository;

import cmr.notep.dao.PersonnesMoralesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonneMoraleRepository extends JpaRepository<PersonnesMoralesEntity, String> {

    @Query(value = "SELECT p.id, p.raisonsociale, p.code, p.datemodification "+
            "FROM personnes_s p "+
            "WHERE p.raisonsociale LIKE %:value% AND p.person_type = 'personnesmorales'", nativeQuery = true)
    List<PersonnesMoralesEntity> findByRaisonSociale(@Param("value")String value);

}
