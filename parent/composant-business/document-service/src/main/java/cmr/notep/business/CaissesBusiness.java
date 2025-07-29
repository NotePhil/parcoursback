package cmr.notep.business;

import cmr.notep.dao.CaissesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MissionsEntity;
import cmr.notep.modele.Caisses;
import cmr.notep.modele.Missions;
import cmr.notep.modele.MouvementCaisses;
import cmr.notep.repository.CaissesRepository;
import cmr.notep.repository.MissionsRepository;
import cmr.notep.repository.MouvementCaissesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class CaissesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public CaissesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Caisses> avoirToutesCaisses() {
        return daoAccessorService.getRepository(CaissesRepository.class).findAll()
                .stream().map(caisses -> dozerMapperBean.map(caisses, Caisses.class))
                .collect(Collectors.toList());
    }

    public Caisses posterCaisse (Caisses caisse){
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(CaissesRepository.class)
                        .save(dozerMapperBean.map(caisse, CaissesEntity.class)),
                Caisses.class
        );
    }


    public Caisses avoirCaisse(String idCaisse)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(CaissesRepository.class).findById(idCaisse)
                .orElseThrow(()->new RuntimeException("Caisse non enregistré")), Caisses.class);
    }
}
