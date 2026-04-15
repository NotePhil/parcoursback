package cmr.notep.repository;

import cmr.notep.dao.DocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentsRepository extends JpaRepository<DocumentsEntity, String> {
    List<DocumentsEntity> findByEtat(Boolean etat);

    List<DocumentsEntity> findByTitreContainingIgnoreCase(String titre);

    List<DocumentsEntity> findByTypeMouvement(String typeMouvement);

    @Query("SELECT d FROM DocumentsEntity d WHERE d.contientRessources = true AND d.etat = true")
    List<DocumentsEntity> findDocumentsWithResources();

    @Query("SELECT d FROM DocumentsEntity d WHERE d.estencaissable = true")
    List<DocumentsEntity> findCashableDocuments();
}
