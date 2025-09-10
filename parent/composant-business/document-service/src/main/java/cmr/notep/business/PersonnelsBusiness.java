package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.PersonnelsEntity;
import cmr.notep.modele.Personnels;
import cmr.notep.repository.PersonnelsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class PersonnelsBusiness  {

    private final DaoAccessorService daoAccessorService;

    public PersonnelsBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public Personnels avoirPersonnel(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PersonnelsRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("personnel inexistante")), Personnels.class);
    }

    public List<Personnels> avoirToutPersonnels() {
        return daoAccessorService.getRepository(PersonnelsRepository.class).findAll()
                .stream().map(personnel ->dozerMapperBean.map(personnel, Personnels.class))
                .collect(Collectors.toList());
    }

    public void supprimerPersonnel(Personnels Personnels)
    {
        daoAccessorService.getRepository(PersonnelsRepository.class)
                .deleteById(Personnels.getId().toString());
    }

    public Personnels posterPersonnel(Personnels personnel) {
        return dozerMapperBean.map( this.daoAccessorService.getRepository(PersonnelsRepository.class)
                .save(dozerMapperBean.map(personnel, PersonnelsEntity.class)), Personnels.class);
    }


}
