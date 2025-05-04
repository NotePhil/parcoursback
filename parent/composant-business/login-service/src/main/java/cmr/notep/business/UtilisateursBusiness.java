package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.UtilisateursEntity;
import cmr.notep.modele.Utilisateurs;
import cmr.notep.repository.UtilisateursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class UtilisateursBusiness {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final DaoAccessorService daoAccessorService ;

    public UtilisateursBusiness(DaoAccessorService daoAccessorService)
    {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Utilisateurs> avoirToutUser() {
        return daoAccessorService.getRepository(UtilisateursRepository.class).findAll().stream().map(user -> dozerMapperBean.map(user, Utilisateurs.class))
                .collect(Collectors.toList());

    }

    public Utilisateurs posterUser(Utilisateurs user)
    {
//        String encodedPassword = passwordEncoder.encode(user.getMdp());
//        user.setMdp(encodedPassword);

        UtilisateursEntity utilisateurEntity = dozerMapperBean.map(user, UtilisateursEntity.class);

        UtilisateursEntity savedEntity = daoAccessorService.getRepository(UtilisateursRepository.class).save(utilisateurEntity);

        return dozerMapperBean.map(savedEntity, Utilisateurs.class);
    }
}
