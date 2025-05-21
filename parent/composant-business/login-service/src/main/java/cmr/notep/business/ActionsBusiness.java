package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Actions;
import cmr.notep.repository.ActionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.LoginConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ActionsBusiness {

    private final DaoAccessorService daoAccessorService ;


    public ActionsBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Actions> avoirToutesActions() {
        return daoAccessorService.getRepository(ActionsRepository.class).findAll()
                .stream().map(actions -> dozerMapperBean.map(actions,Actions.class))
                .collect(Collectors.toList());
    }
}
