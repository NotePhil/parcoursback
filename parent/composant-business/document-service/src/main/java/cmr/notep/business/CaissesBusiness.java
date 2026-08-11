package cmr.notep.business;

import cmr.notep.dao.CaissesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Caisses;
import cmr.notep.modele.DetailsJson;
import cmr.notep.repository.CaissesRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Slf4j
@Transactional
public class CaissesBusiness {

    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;

    public CaissesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Caisses avoirCaisse(@NonNull String idCaisse) throws ParcoursException {
        CaissesEntity caisseEntity = this.daoAccessorService.getRepository(CaissesRepository.class)
                .findById(idCaisse)
                .orElseThrow(() -> new ParcoursException(
                        ParcoursExceptionCodeEnum.NOT_FOUND,
                        "Caisse introuvable: " + idCaisse));
        return dozerMapperBean.map(caisseEntity, Caisses.class);
    }

    public List<Caisses> avoirToutCaisses() {
        return daoAccessorService.getRepository(CaissesRepository.class).findAll().stream().map(caisse -> dozerMapperBean.map(caisse, Caisses.class)).toList();
    }

    public void supprimerCaisse(@NonNull Caisses caisse) {
        daoAccessorService.getRepository(CaissesRepository.class).deleteById(caisse.getId());
    }

    /**
     * Crée une caisse si l'id est absent, sinon met à jour uniquement les champs autorisés.
     * Les champs sensibles type/solde/detailsJson sont toujours conservés depuis l'existant.
     */
    public Caisses posterCaisse(@NonNull Caisses caisse) throws ParcoursException {
        CaissesRepository repository = this.daoAccessorService.getRepository(CaissesRepository.class);

        if (caisse.getId() == null || caisse.getId().isBlank()) {
            CaissesEntity createdToSave = dozerMapperBean.map(caisse, CaissesEntity.class);
            validerCoherenceSoldeEtDetails(createdToSave);
            CaissesEntity created = repository.save(createdToSave);
            return dozerMapperBean.map(created, Caisses.class);
        }

        CaissesEntity existing = repository.findById(caisse.getId())
                .orElseThrow(() -> new ParcoursException(
                        ParcoursExceptionCodeEnum.NOT_FOUND,
                        "Mise a jour impossible: caisse introuvable: " + caisse.getId()));

        CaissesEntity entityToSave = dozerMapperBean.map(caisse, CaissesEntity.class);
        entityToSave.setType(existing.getType());
        entityToSave.setSolde(existing.getSolde());
        entityToSave.setDetailsJson(existing.getDetailsJson());
        validerCoherenceSoldeEtDetails(entityToSave);

        CaissesEntity saved = repository.save(entityToSave);
        return dozerMapperBean.map(saved, Caisses.class);
    }

    private void validerCoherenceSoldeEtDetails(CaissesEntity caisseEntity) throws ParcoursException {
        boolean typeCash = caisseEntity.getType() != null && "cash".equalsIgnoreCase(caisseEntity.getType().trim());
        DetailsJson detailsJson = caisseEntity.getDetailsJson();

        // Cas explicitement autorise: caisse cash a zero sans details.
        if (typeCash && Double.compare(caisseEntity.getSolde(), 0d) == 0 && detailsJson == null) {
            return;
        }

        if (detailsJson != null) {
            if (!typeCash) {
                throw new ParcoursException(
                        ParcoursExceptionCodeEnum.INVALID_INPUT,
                        "detailsJson est autorise uniquement pour une caisse de type cash");
            }

            double soldeCalcule = calculerSoldeDepuisDetails(detailsJson);
            if (Double.compare(caisseEntity.getSolde(), soldeCalcule) != 0) {
                throw new ParcoursException(
                        ParcoursExceptionCodeEnum.INVALID_INPUT,
                        "Solde incoherent avec detailsJson. Attendu: " + soldeCalcule + ", recu: " + caisseEntity.getSolde());
            }
        }
    }

    private double calculerSoldeDepuisDetails(DetailsJson detailsJson) {
        return valeur(detailsJson.getX10000()) * 10000d
                + valeur(detailsJson.getX5000()) * 5000d
                + valeur(detailsJson.getX1000()) * 1000d
                + valeur(detailsJson.getX500B()) * 500d
                + valeur(detailsJson.getX500()) * 500d
                + valeur(detailsJson.getX100()) * 100d
                + valeur(detailsJson.getX50()) * 50d
                + valeur(detailsJson.getX25()) * 25d
                + valeur(detailsJson.getX10()) * 10d
                + valeur(detailsJson.getX5()) * 5d
                + valeur(detailsJson.getX2()) * 2d
                + valeur(detailsJson.getX1());
    }

    private int valeur(Integer quantite) {
        return quantite == null ? 0 : quantite;
    }
}
