package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Utilisateurs;
import cmr.notep.repository.UtilisateursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class UtilisateursBusiness {

    private final DaoAccessorService daoAccessorService ;

    public UtilisateursBusiness(DaoAccessorService daoAccessorService)
    {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Utilisateurs> avoirToutUser() {
        return daoAccessorService.getRepository(UtilisateursRepository.class).findAll().stream().map(user -> dozerMapperBean.map(user, Utilisateurs.class))
                .collect(Collectors.toList());

    }
}
