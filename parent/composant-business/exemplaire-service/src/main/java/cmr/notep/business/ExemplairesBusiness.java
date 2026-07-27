package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.dao.ExemplairesEntity;
import cmr.notep.gateway.DocumentGatewayStrategy;
import cmr.notep.helper.ExemplaireConvertirHelper;
import cmr.notep.modele.Exemplaires;
import cmr.notep.modele.ExemplairesInterne;
import cmr.notep.exemplaire.repository.ExemplairesRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cmr.notep.config.ExemplaireConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ExemplairesBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final DocumentGatewayStrategy documentGatewayApi;

    public ExemplairesBusiness(DaoAccessorService daoAccessorService, DocumentGatewayStrategy documentGatewayApi) {
        this.daoAccessorService = daoAccessorService;
        this.documentGatewayApi = documentGatewayApi;
    }
    public List<Exemplaires> avoirTousExemplaires() throws ParcoursException {
        List<Exemplaires> listExemplaires = new ArrayList<>();
        for (ExemplairesEntity exemplairesEntity : this.daoAccessorService.getRepository(ExemplairesRepository.class).findAll()) {
            Exemplaires exemplaires = ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentGatewayApi,
                    dozerMapperBean.map(exemplairesEntity, ExemplairesInterne.class));
            listExemplaires.add(exemplaires);
        }
        return listExemplaires;
    }
    private ExemplairesInterne avoirExemplaireInterne(String idExemplaire) throws ParcoursException {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ExemplairesRepository.class)
                .findById(idExemplaire)
                .orElseThrow(()-> new ParcoursException(ParcoursExceptionCodeEnum.INTERNAL_ERROR,"Attribut introuvable")), ExemplairesInterne.class);
    }
    public Exemplaires avoirExemplaire(String idExemplaire) throws ParcoursException {
        ExemplairesInterne exemplairesInterne = avoirExemplaireInterne(idExemplaire);
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentGatewayApi,exemplairesInterne);
    }

    public Exemplaires posterExemplaire(@NonNull Exemplaires exemplaire) throws ParcoursException {
        ExemplairesInterne exemplairesInterne = ExemplaireConvertirHelper.convertirExemplaireEnExemplaireInternes(exemplaire);
        exemplairesInterne = dozerMapperBean.map(daoAccessorService.getRepository(ExemplairesRepository.class)
                .save(dozerMapperBean.map(exemplairesInterne, ExemplairesEntity.class)), ExemplairesInterne.class);
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentGatewayApi,exemplairesInterne);
    }

    public void supprimerExemplaire(@NonNull Exemplaires exemplaire) {
        log.debug("[supprimerExemplaire] Suppression de l'exemplaire avec l'ID : " + exemplaire.getId());
        this.daoAccessorService.getRepository(ExemplairesRepository.class)
                .deleteById(exemplaire.getId().toString());
    }

    public Exemplaires modifierExemplaire(@NonNull Exemplaires exemplaire) throws ParcoursException {
        log.debug("[modifierExemplaire] Modification de l'exemplaire avec l'ID : " + exemplaire.getId());
        // Vérifier que l'exemplaire existe avant de le modifier
        this.daoAccessorService.getRepository(ExemplairesRepository.class)
                .findById(exemplaire.getId().toString())
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND, "Exemplaire introuvable"));

        ExemplairesInterne exemplairesInterne = ExemplaireConvertirHelper.convertirExemplaireEnExemplaireInternes(exemplaire);
        exemplairesInterne = dozerMapperBean.map(daoAccessorService.getRepository(ExemplairesRepository.class)
                .save(dozerMapperBean.map(exemplairesInterne, ExemplairesEntity.class)), ExemplairesInterne.class);
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentGatewayApi, exemplairesInterne);
    }
}
