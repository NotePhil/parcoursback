package cmr.notep.business;

import cmr.notep.dao.CaissesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Caisses;
import cmr.notep.repository.CaissesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class CaissesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public CaissesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public Caisses avoirCaisse(String idCaisse) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(CaissesRepository.class).findById(idCaisse).orElseThrow(() -> new RuntimeException("Caisse introuvable")), Caisses.class);
    }

    public List<Caisses> avoirToutCaisses() {
        return daoAccessorService.getRepository(CaissesRepository.class).findAll().stream().map(caisse -> dozerMapperBean.map(caisse, Caisses.class)).toList();
    }

    public void supprimerCaisse(Caisses caisse) {
        daoAccessorService.getRepository(CaissesRepository.class).deleteById(caisse.getId());
    }

    public Caisses posterCaisse(Caisses caisse) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(CaissesRepository.class).save(dozerMapperBean.map(caisse, CaissesEntity.class)), Caisses.class);
    }
}
