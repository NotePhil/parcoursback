package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Comptes;
import cmr.notep.repository.ComptesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ComptesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public ComptesBusiness(DaoAccessorService daoAccessorService)
    {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Comptes> avoirToutComptes() {
        return daoAccessorService.getRepository(ComptesRepository.class).findAll()
                .stream().map(comptes -> dozerMapperBean.map(comptes, Comptes.class))
                .collect(Collectors.toList());
    }
}
