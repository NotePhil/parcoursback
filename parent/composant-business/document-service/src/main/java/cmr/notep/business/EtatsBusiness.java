package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.EtatsEntity;
import cmr.notep.modele.Etats;
import cmr.notep.repository.EtatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Etats> avoirTousEtats()
    {
        return daoAccessorService.getRepository(EtatsRepository.class).findAll()
                .stream().map(etat -> dozerMapperBean.map(etat, Etats.class))
                .collect(Collectors.toList());
    }

    public Etats posterEtat (Etats etat){
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(EtatsRepository.class)
                        .save(dozerMapperBean.map(etat, EtatsEntity.class)),
                Etats.class
        );
    }

}
