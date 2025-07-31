package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.ParcoursEntity;
import cmr.notep.modele.Parcours;
import cmr.notep.repository.ParcoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ParcoursBusiness {

    private final DaoAccessorService daoAccessorService ;

    public ParcoursBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Parcours> avoirToutesparcour() {
        return this.daoAccessorService.getRepository(ParcoursRepository.class).findAll()
                .stream().map(parcour -> dozerMapperBean.map(parcour, Parcours.class))
                .collect(Collectors.toList());
    }

    public Parcours posterparcours (Parcours parcours){
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(ParcoursRepository.class)
                        .save(dozerMapperBean.map(parcours, ParcoursEntity.class)),
                Parcours.class
        );
    }


    public Parcours avoirParcour(String idparcour)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ParcoursRepository.class).findById(idparcour)
                .orElseThrow(()->new RuntimeException("Parcour non enregistré")), Parcours.class);
    }

}
