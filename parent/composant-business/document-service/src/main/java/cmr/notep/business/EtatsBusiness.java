package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Etats;
import cmr.notep.repository.EtatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class EtatsBusiness {
    private final DaoAccessorService daoAccessorService ;

    public EtatsBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }
    public Etats avoirEtat(String idEtat) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(EtatsRepository.class)
                .findById(idEtat)
                .orElseThrow(()-> new RuntimeException("Etat introuvable")),Etats.class);
    }
}
