package cmr.notep.business;

import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.dao.PrecoMouvementsEntity;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.repository.PrecoMouvementsRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.dao.DaoAccessorService;
import lombok.extern.slf4j.Slf4j;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PrecomouvementsBusiness {
    private final DaoAccessorService daoAccessorService ;

    public PrecomouvementsBusiness(DaoAccessorService daoAccessorService) { this.daoAccessorService = daoAccessorService ;}

    public List <PrecoMouvements> avoirTousPrecouvement()
    {
        return daoAccessorService.getRepository(PrecoMouvementsRepository.class).findAll()
                .stream().map(precomouvement -> dozerMapperBean.map(precomouvement,PrecoMouvements.class))
                .collect(Collectors.toList());
    }

    public PrecoMouvements avoirPrecomouvements(String idPreco)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(PrecoMouvementsRepository.class).findById(idPreco)
                .orElseThrow(()->new RuntimeException("Missions non enregistré")), PrecoMouvements.class);
    }

    public PrecoMouvements posterPrecomouvement (PrecoMouvements preco){
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                        .save(dozerMapperBean.map(preco, PrecoMouvementsEntity.class)),
                PrecoMouvements.class
        );
    }

    public void supprimerPrecomouvement(PrecoMouvements precomouvement) {
        daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .deleteById(precomouvement.getId().toString());
    }

    public PrecoMouvements modifierPrecomouvement(PrecoMouvements precomouvement) {
        // Vérifier que le précomouvement existe avant de le modifier
        this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .findById(precomouvement.getId().toString())
                .orElseThrow(() -> new RuntimeException("Précomouvement non enregistré"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                        .save(dozerMapperBean.map(precomouvement, PrecoMouvementsEntity.class)),
                PrecoMouvements.class);
    }
}
