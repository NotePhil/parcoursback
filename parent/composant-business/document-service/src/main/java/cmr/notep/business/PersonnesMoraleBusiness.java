package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.PersonnesMorale;
import cmr.notep.repository.PersonneMoraleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PersonnesMoraleBusiness {

    private final DaoAccessorService daoAccessorService;

    public PersonnesMoraleBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public PersonnesMorale avoirPersonneMorale(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PersonneMoraleRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("Personnes morales inexistante")), PersonnesMorale.class);}


    public List<PersonnesMorale> avoirListPersonnesMoraleByelmnt(String value)
    {
        return daoAccessorService.getRepository(PersonneMoraleRepository.class).findByRaisonSociale(value)
                .stream().map(cat ->dozerMapperBean.map(cat, PersonnesMorale.class))
                .collect(Collectors.toList());
    }

    public List<PersonnesMorale> avoirToutPersonnesMorale() {

        return this.daoAccessorService.getRepository(PersonneMoraleRepository.class).findAll()
                .stream().map(personnesmorale ->dozerMapperBean.map(personnesmorale, PersonnesMorale.class))
                .toList();

    }
}
