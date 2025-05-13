package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RessourcesRepositoryImpl implements RessourcesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RessourcesEntity> triRessources(String request) {
        Query query = entityManager.createNativeQuery(request, RessourcesEntity.class);
        return query.getResultList();
    }
}