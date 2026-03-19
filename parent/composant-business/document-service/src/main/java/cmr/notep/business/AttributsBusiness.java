package cmr.notep.business;

import cmr.notep.dao.AttributsEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Attributs;
import cmr.notep.repository.AttributsRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class AttributsBusiness {

    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;

    public AttributsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean)
    {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Attributs avoirAttribut(String idAttribut) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(AttributsRepository.class)
                .findById(idAttribut)
                .orElseThrow(()-> new RuntimeException("Attribut introuvable")),Attributs.class);
    }

    public List<Attributs> avoirToutAttribut() {
        return daoAccessorService.getRepository(AttributsRepository.class).findAll()
                .stream().map(attrib -> dozerMapperBean.map(attrib,Attributs.class))
                .collect(Collectors.toList());
    }

    public void supprimerAttribut(Attributs attrib)
    {
        daoAccessorService.getRepository(AttributsRepository.class)
                .deleteById(attrib.getId());
    }

    public Attributs posterAttribut(Attributs attributs) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(AttributsRepository.class)
                .save(dozerMapperBean.map(attributs, AttributsEntity.class)), Attributs.class );
    }
}
