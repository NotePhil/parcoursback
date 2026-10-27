package cmr.notep.business;

import cmr.notep.dao.CaissesReellesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MouvementSoldeCaissesEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.exemplaire.repository.CaissesReellesRepository;
import cmr.notep.exemplaire.repository.MouvementSoldeCaissesRepository;
import cmr.notep.modele.MouvementSoldeCaisses;
import cmr.notep.modele.StatutMouvement;
import cmr.notep.modele.TypeMouvement;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service métier pour gérer les transactions de soldes des caisses
 * Les modifications de solde doivent TOUJOURS passer par ce service
 * et ne peuvent pas être modifiées directement
 */
@Component
@Slf4j
@Transactional
public class SoldeCaissesTransactionBusiness {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public SoldeCaissesTransactionBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerBeanMapper) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerBeanMapper;
    }

    /**
     * Enregistrer une transaction de mouvement pour une caisse
     */
    public MouvementSoldeCaisses enregistrerMouvement(
            String caissesId,
            Double soldeMovementee,
            String typeTransaction,
            String description,
            String motif,
            String codeReference,
            String createdBy) {

        CaissesReellesEntity caisse = daoAccessorService.getRepository(CaissesReellesRepository.class)
                .findById(caissesId)
                .orElseThrow(() -> new RuntimeException("Caisse inexistante: " + caissesId));

        MouvementSoldeCaissesEntity mouvement = new MouvementSoldeCaissesEntity();
        mouvement.setCaissesEntity(caisse);
        mouvement.setSoldeInitiale(caisse.getSolde());
        mouvement.setSoldeMovementee(soldeMovementee);

        // Calculer le nouveau solde selon le type de transaction
        Double nouveauSolde = caisse.getSolde();
        if (TypeMouvement.Ajout.name().equalsIgnoreCase(typeTransaction)) {
            nouveauSolde += soldeMovementee;
        } else if (TypeMouvement.Reduire.name().equalsIgnoreCase(typeTransaction)) {
            if (caisse.getSolde() < soldeMovementee) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_OPERATION,
                        "Solde insuffisant. Disponible: " + caisse.getSolde() +
                        ", Demandé: " + soldeMovementee);
            }
            nouveauSolde -= soldeMovementee;
        } else if (TypeMouvement.Neutre.name().equalsIgnoreCase(typeTransaction)) {
            nouveauSolde = soldeMovementee;
        }

        mouvement.setSoldeFinale(nouveauSolde);
        mouvement.setTypeTransaction(typeTransaction);
        mouvement.setDescription(description);
        mouvement.setMotif(motif);
        mouvement.setCodeReference(codeReference);
        mouvement.setCreatedBy(createdBy);
        mouvement.setDateCreation(new Date());
        mouvement.setStatut(StatutMouvement.VALIDE.getStatutMvt());
        mouvement.setValidee(true);

        MouvementSoldeCaissesEntity saved = daoAccessorService
                .getRepository(MouvementSoldeCaissesRepository.class)
                .save(mouvement);

        log.info("Mouvement de caisse enregistré: {} - Type: {} - Montant: {}",
                caissesId, typeTransaction, soldeMovementee);

        return dozerMapperBean.map(saved, MouvementSoldeCaisses.class);
    }

    /**
     * Valider et appliquer un mouvement
     */
    @Transactional
    public MouvementSoldeCaisses validerMouvement(String mouvementId, String valideeBy) {
        MouvementSoldeCaissesEntity mouvement = daoAccessorService
                .getRepository(MouvementSoldeCaissesRepository.class)
                .findById(mouvementId)
                .orElseThrow(() -> new RuntimeException("Mouvement inexistant: " + mouvementId));

        if (mouvement.getValidee()) {
            throw new RuntimeException("Ce mouvement a déjà été validé");
        }

        // Appliquer la modification à la caisse
        CaissesReellesEntity caisse = mouvement.getCaissesEntity();
        caisse.setSolde(mouvement.getSoldeFinale());

        daoAccessorService.getRepository(CaissesReellesRepository.class).save(caisse);

        // Marquer le mouvement comme validé
        mouvement.setValidee(true);
        mouvement.setValideeBy(valideeBy);
        mouvement.setDateValidation(new Date());
        mouvement.setStatut(StatutMouvement.VALIDE.getStatutMvt());

        MouvementSoldeCaissesEntity saved = daoAccessorService
                .getRepository(MouvementSoldeCaissesRepository.class)
                .save(mouvement);

        log.info("Mouvement de caisse validé: {}", mouvementId);
        return dozerMapperBean.map(saved, MouvementSoldeCaisses.class);
    }

    /**
     * Rejeter un mouvement
     */
    @Transactional
    public void rejeterMouvement(String mouvementId, String raison) {
        MouvementSoldeCaissesEntity mouvement = daoAccessorService
                .getRepository(MouvementSoldeCaissesRepository.class)
                .findById(mouvementId)
                .orElseThrow(() -> new RuntimeException("Mouvement inexistant: " + mouvementId));

        if (mouvement.getValidee()) {
            throw new RuntimeException("Impossible de rejeter un mouvement déjà validé");
        }

        mouvement.setStatut(StatutMouvement.REJETEE.getStatutMvt());
        mouvement.setDescription(mouvement.getDescription() + " [REJETÉE: " + raison + "]");

        daoAccessorService.getRepository(MouvementSoldeCaissesRepository.class).save(mouvement);
        log.info("Mouvement de caisse rejeté: {}", mouvementId);
    }

    /**
     * Obtenir l'historique des mouvements pour une caisse
     */
    public List<MouvementSoldeCaisses> obtenirHistorique(String caissesId) {
        return daoAccessorService.getRepository(MouvementSoldeCaissesRepository.class)
                .findByCaissesEntity_Id(caissesId)
                .stream()
                .map(m -> dozerMapperBean.map(m, MouvementSoldeCaisses.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtenir les mouvements en attente de validation
     */
    public List<MouvementSoldeCaisses> obtenirMouvementsEnAttente(String caissesId) {
        return daoAccessorService.getRepository(MouvementSoldeCaissesRepository.class)
                .findByCaissesEntity_Id(caissesId)
                .stream()
                .filter(m -> StatutMouvement.EN_ATTENTE.equals(m.getStatut()))
                .map(m -> dozerMapperBean.map(m, MouvementSoldeCaisses.class))
                .collect(Collectors.toList());
    }
}
