package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;


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