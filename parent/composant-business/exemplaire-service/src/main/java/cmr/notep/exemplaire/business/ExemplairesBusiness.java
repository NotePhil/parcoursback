package cmr.notep.exemplaire.business;

import cmr.notep.api.*;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.exemplaire.dao.ExemplairesEntity;
import cmr.notep.exemplaire.helper.ExemplaireConvertirHelper;
import cmr.notep.exemplaire.modele.*;
import cmr.notep.exemplaire.repository.ExemplairesRepository;
import cmr.notep.modele.Documents;
import cmr.notep.modele.Personnes;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.Validations;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.exemplaire.config.ExemplaireConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class ExemplairesBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final IDocumentsApi documentsApi;
    private final IPersonnesApi personnesApi;
    private final IAttributsApi attributsApi;
    private final IDistributeursApi distributeursApi;
    private final IEtatsApi etatsApi;
    private final IValidationsApi validationsApi;
    private final IRessourcesApi ressourcesApi;
    private final IPrecoMouvementsApi precoMouvementsApi;
    private final IPersonnelsApi personnelsApi;

    public ExemplairesBusiness(DaoAccessorService daoAccessorService, IDocumentsApi documentsApi, IPersonnesApi personnesApi, IAttributsApi attributsApi, IDistributeursApi distributeursApi, IEtatsApi etatsApi, IValidationsApi validationsApi, IRessourcesApi ressourcesApi, IPrecoMouvementsApi precoMouvementsApi, IPersonnelsApi personnelsApi) {
        this.daoAccessorService = daoAccessorService;
        this.documentsApi = documentsApi;
        this.personnesApi = personnesApi;
        this.attributsApi = attributsApi;
        this.distributeursApi = distributeursApi;
        this.etatsApi = etatsApi;
        this.validationsApi = validationsApi;
        this.ressourcesApi = ressourcesApi;
        this.precoMouvementsApi = precoMouvementsApi;
        this.personnelsApi = personnelsApi;
    }
    public List<Exemplaires> avoirTousExemplaires() throws ParcoursException {
        List<Exemplaires> listExemplaires = new ArrayList<>();
        for (ExemplairesEntity exemplairesEntity : this.daoAccessorService.getRepository(ExemplairesRepository.class).findAll()) {
            Exemplaires exemplaires = ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentsApi, personnesApi, attributsApi,
                    precoMouvementsApi, ressourcesApi, distributeursApi,
                    personnelsApi, validationsApi,  etatsApi,
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
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentsApi, personnesApi, attributsApi,
                 precoMouvementsApi, ressourcesApi, distributeursApi,
                 personnelsApi, validationsApi,  etatsApi,exemplairesInterne);
    }

    public Exemplaires posterExemplaire(@NonNull Exemplaires exemplaire) throws ParcoursException {
        ExemplairesInterne exemplairesInterne = ExemplaireConvertirHelper.convertirExemplaireEnExemplaireInternes(exemplaire);
        exemplairesInterne = dozerMapperBean.map(daoAccessorService.getRepository(ExemplairesRepository.class)
                .save(dozerMapperBean.map(exemplairesInterne, ExemplairesEntity.class)), ExemplairesInterne.class);
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentsApi, personnesApi, attributsApi,
                precoMouvementsApi, ressourcesApi, distributeursApi,
                personnelsApi, validationsApi,  etatsApi,exemplairesInterne);
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
        return ExemplaireConvertirHelper.convertirExemplaireInterneEnExemplaire(documentsApi, personnesApi, attributsApi,
                precoMouvementsApi, ressourcesApi, distributeursApi,
                personnelsApi, validationsApi, etatsApi, exemplairesInterne);
    }
}
