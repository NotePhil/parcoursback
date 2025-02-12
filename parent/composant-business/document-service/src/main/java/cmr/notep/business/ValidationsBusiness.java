package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Validations;
import cmr.notep.repository.ValidationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;
@Component
@Slf4j
@Transactional
public class ValidationsBusiness {
    private final DaoAccessorService daoAccessorService ;

    public ValidationsBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public Validations avoirValidation(String idValidation) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ValidationsRepository.class)
                .findById(idValidation)
                .orElseThrow(()-> new RuntimeException("Validation introuvable")),Validations.class);
    }
}
