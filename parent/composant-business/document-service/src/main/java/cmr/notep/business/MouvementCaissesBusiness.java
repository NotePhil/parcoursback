package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Attributs;
import cmr.notep.modele.MouvementCaisses;
import cmr.notep.repository.AttributsRepository;
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
public class MouvementCaissesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public MouvementCaissesBusiness(DaoAccessorService daoAccessorService)
    {
        this.daoAccessorService = daoAccessorService;
    }

    public List<MouvementCaisses> avoirToutMouvementCaisses() {
        return daoAccessorService.getRepository(MouvementCaissesRepository.class).findAll()
                .stream().map(mouvcaisses -> dozerMapperBean.map(mouvcaisses, MouvementCaisses.class))
                .collect(Collectors.toList());
    }

}
