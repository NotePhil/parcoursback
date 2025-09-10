package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Validations;
import cmr.notep.repository.ValidationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Validations> avoirToutValidations() {
        return daoAccessorService.getRepository(ValidationsRepository.class).findAll()
                .stream().map(validation -> dozerMapperBean.map(validation, Validations.class))
                .collect(Collectors.toList());
    }

    public void supprimerValidation(Validations validation) {
        daoAccessorService.getRepository(ValidationsRepository.class)
                .deleteById(validation.getId().toString());
    }

    public Validations posterValidation(Validations validation) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(ValidationsRepository.class)
                        .save(dozerMapperBean.map(validation, cmr.notep.dao.ValidationsEntity.class)),
                Validations.class);
    }

    public Validations modifierValidation(Validations validation) {
        // Vérifier que la validation existe avant de la modifier
        this.daoAccessorService.getRepository(ValidationsRepository.class)
                .findById(validation.getId().toString())
                .orElseThrow(() -> new RuntimeException("Validation introuvable"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(ValidationsRepository.class)
                        .save(dozerMapperBean.map(validation, cmr.notep.dao.ValidationsEntity.class)),
                Validations.class);
    }
}
