package cmr.notep.repository;

import cmr.notep.dao.RessourcesEntity;

import java.util.List;

public interface RessourcesRepositoryCustom {
    List<RessourcesEntity> triRessources(String request);
}
