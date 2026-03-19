package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.modele.Ressources;
import cmr.notep.repository.RessourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class RessourcesBusiness {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public RessourcesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Ressources avoirRessource(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(RessourcesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("ressource innexistante inexistante")), Ressources.class);
    }

    public List<Ressources> avoirToutRessources() {
        return daoAccessorService.getRepository(RessourcesRepository.class).findAll()
                .stream().map(ressource ->dozerMapperBean.map(ressource, Ressources.class))
                .collect(Collectors.toList());
    }

    public void supprimerRessource(Ressources Ressources)
    {
        daoAccessorService.getRepository(RessourcesRepository.class)
                .deleteById(Ressources.getId().toString());
    }

    public Ressources posterRessource(Ressources Ressources) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(RessourcesRepository.class)
                .save(dozerMapperBean.map(Ressources, RessourcesEntity.class)), Ressources.class);
    }

}
