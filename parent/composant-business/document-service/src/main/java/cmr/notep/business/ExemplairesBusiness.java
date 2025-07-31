package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.ExemplairesEntity;
import cmr.notep.modele.Exemplaires;
import cmr.notep.repository.ExemplairesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional

public class ExemplairesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public ExemplairesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Exemplaires> avoirTousExemplaires()
    {
        return daoAccessorService.getRepository(ExemplairesRepository.class).findAll()
                .stream().map(exemplaires -> dozerMapperBean.map(exemplaires, Exemplaires.class))
                .collect(Collectors.toList());
    }

    public Exemplaires avoirExemplaires(String idExemplaire)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ExemplairesRepository.class).findById(idExemplaire)
                .orElseThrow(()->new RuntimeException("Exemplaire non enregistré")), Exemplaires.class);
    }

    public Exemplaires posterExemplaire (Exemplaires exemplaires){
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(ExemplairesRepository.class)
                        .save(dozerMapperBean.map(exemplaires, ExemplairesEntity.class)),
                Exemplaires.class
        );
    }

}
