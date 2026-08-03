package cmr.notep.repository;

import cmr.notep.dao.DocEtatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocEtatsRepository extends JpaRepository<DocEtatsEntity, String> {
    List<DocEtatsEntity> findByDocumentsEntityId(String documents_id);
}
