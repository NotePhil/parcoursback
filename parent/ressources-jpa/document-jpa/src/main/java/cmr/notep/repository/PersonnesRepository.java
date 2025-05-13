package cmr.notep.repository;

import cmr.notep.dao.DistributeursEntity;
import cmr.notep.dao.PersonnesEntity;
import cmr.notep.dao.PersonnesMoralesEntity;
import cmr.notep.dao.PersonnesPhysiquesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnesRepository extends JpaRepository<PersonnesEntity, String> {

    @Query(value = "SELECT p.id, p.comptes_id,p.raisonsociale, p.code, p.datemodification, "+
            "p.adresse,p.mail,p.telephone,p.qrcodevalue,p.datecreation "+
            "FROM personnes p "+
            "WHERE p.raisonsociale LIKE %:value% AND p.person_type = 'personnesmorales'", nativeQuery = true)
    List<PersonnesMoralesEntity> findByRaisonSocialeOnPersonneMorale(@Param("value")String value);

    @Query(value = "SELECT p.id,p.comptes_id, p.nom, p.prenom,p.adresse,p.datecreation,p.qrcodevalue ,"+
            " p.sexe,p.mail,p.telephone, p.datenaissance, p.datemodification " +
            "FROM personnes p " +
            "WHERE (p.nom LIKE %:value% OR p.prenom LIKE %:value%) AND p.person_type = 'personnesphysique'", nativeQuery = true)
    List<PersonnesPhysiquesEntity> findByNomOrByPrenomOnPersonnePhysique(@Param("value")String value);

    @Query(value = "SELECT p.id,p.comptes_id, p.datemodification,p.adresse,p.datecreation,p.mail,p.telephone,"+
            " p.qrcodevalue ,p.code, p.raisonsociale "+
            "FROM personnes p "+
            "WHERE p.raisonsociale LIKE %:value% AND p.person_type = 'distributeurs'", nativeQuery = true)
    List<DistributeursEntity> findByRaisonSocialeOnDistributeurs(@Param("value") String value);

    @Query(value = "SELECT p.id,p.comptes_id, p.datemodification,p.adresse,p.datecreation,"+
            "p.mail,p.telephone,p.datenaissance,p.nom,p.prenom,p.sexe,p.comptes_id,"+
            " p.qrcodevalue ,p.code,p.person_type, p.raisonsociale "+
            "FROM personnes p ", nativeQuery = true)
    List<PersonnesEntity> findAllPersonne();
}
