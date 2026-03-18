package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.MouvementSoldeRessourcesEntity;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.modele.MouvementSoldeRessource;
import cmr.notep.repository.MouvementSoldeRessourcesRepository;
import cmr.notep.repository.RessourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

/**
 * Service métier pour gérer les transactions de soldes des ressources
 * Les modifications de solde doivent TOUJOURS passer par ce service
 * et ne peuvent pas être modifiées directement
 */
@Component
@Slf4j
@Transactional
public class SoldeRessourcesTransactionBusiness {

    private final DaoAccessorService daoAccessorService;

    public SoldeRessourcesTransactionBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    /**
     * Enregistrer une transaction de mouvement pour une ressource
     * @param ressourcesId ID de la ressource
     * @param mouvement Détails du mouvement (type, quantité, motif, etc.)
     * @param createdBy Utilisateur qui crée la transaction
     * @return La transaction enregistrée
     */
    public MouvementSoldeRessource enregistrerMouvement(
            String ressourcesId,
            Integer quantiteMouvementee,
            String typeTransaction,
            String description,
            String motif,
            String codeReference,
            String createdBy) {

        RessourcesEntity ressource = daoAccessorService.getRepository(RessourcesRepository.class)
                .findById(ressourcesId)
                .orElseThrow(() -> new RuntimeException("Ressource inexistante: " + ressourcesId));

        MouvementSoldeRessourcesEntity mouvement = new MouvementSoldeRessourcesEntity();
        mouvement.setRessourcesEntity(ressource);
        mouvement.setQuantiteInitiale(ressource.getQuantite());
        mouvement.setQuantiteMouvementee(quantiteMouvementee);

        // Calculer la nouvelle quantité selon le type de transaction
        Integer nouvelleQuantite = ressource.getQuantite();
        if ("ENTREE".equals(typeTransaction)) {
            nouvelleQuantite += quantiteMouvementee;
        } else if ("SORTIE".equals(typeTransaction)) {
            if (ressource.getQuantite() < quantiteMouvementee) {
                throw new RuntimeException(
                        "Quantité insuffisante. Disponible: " + ressource.getQuantite() +
                        ", Demandée: " + quantiteMouvementee);
            }
            nouvelleQuantite -= quantiteMouvementee;
        } else if ("AJUSTEMENT".equals(typeTransaction)) {
            nouvelleQuantite = quantiteMouvementee;
        }

        mouvement.setQuantiteFinale(nouvelleQuantite);
        mouvement.setTypeTransaction(typeTransaction);
        mouvement.setDescription(description);
        mouvement.setMotif(motif);
        mouvement.setCodeReference(codeReference);
        mouvement.setCreatedBy(createdBy);
        mouvement.setDateCreation(new Date());
        mouvement.setStatut("EN_ATTENTE");
        mouvement.setValidee(false);

        MouvementSoldeRessourcesEntity saved = daoAccessorService
                .getRepository(MouvementSoldeRessourcesRepository.class)
                .save(mouvement);

        log.info("Mouvement de ressource enregistré: {} - Type: {} - Quantité: {}",
                ressourcesId, typeTransaction, quantiteMouvementee);

        return dozerMapperBean.map(saved, MouvementSoldeRessource.class);
    }

    /**
     * Valider et appliquer un mouvement
     */
    @Transactional
    public MouvementSoldeRessource validerMouvement(String mouvementId, String valideeBy) {
        MouvementSoldeRessourcesEntity mouvement = daoAccessorService
                .getRepository(MouvementSoldeRessourcesRepository.class)
                .findById(mouvementId)
                .orElseThrow(() -> new RuntimeException("Mouvement inexistant: " + mouvementId));

        if (mouvement.getValidee()) {
            throw new RuntimeException("Ce mouvement a déjà été validé");
        }

        // Appliquer la modification à la ressource
        RessourcesEntity ressource = mouvement.getRessourcesEntity();
        ressource.setQuantite(mouvement.getQuantiteFinale());
        ressource.setDateModification(new Date());

        daoAccessorService.getRepository(RessourcesRepository.class).save(ressource);

        // Marquer le mouvement comme validé
        mouvement.setValidee(true);
        mouvement.setValideeBy(valideeBy);
        mouvement.setDateValidation(new Date());
        mouvement.setStatut("VALIDEE");

        MouvementSoldeRessourcesEntity saved = daoAccessorService
                .getRepository(MouvementSoldeRessourcesRepository.class)
                .save(mouvement);

        log.info("Mouvement de ressource validé: {}", mouvementId);
        return dozerMapperBean.map(saved, MouvementSoldeRessource.class);
    }

    /**
     * Rejeter un mouvement
     */
    @Transactional
    public void rejeterMouvement(String mouvementId, String raison) {
        MouvementSoldeRessourcesEntity mouvement = daoAccessorService
                .getRepository(MouvementSoldeRessourcesRepository.class)
                .findById(mouvementId)
                .orElseThrow(() -> new RuntimeException("Mouvement inexistant: " + mouvementId));

        if (mouvement.getValidee()) {
            throw new RuntimeException("Impossible de rejeter un mouvement déjà validé");
        }

        mouvement.setStatut("REJETEE");
        mouvement.setDescription(mouvement.getDescription() + " [REJETÉE: " + raison + "]");

        daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class).save(mouvement);
        log.info("Mouvement de ressource rejeté: {}", mouvementId);
    }

    /**
     * Obtenir l'historique des mouvements pour une ressource
     */
    public List<MouvementSoldeRessource> obtenirHistorique(String ressourcesId) {
        return daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class)
                .findByRessourcesEntity_Id(ressourcesId)
                .stream()
                .map(m -> dozerMapperBean.map(m, MouvementSoldeRessource.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtenir les mouvements en attente de validation
     */
    public List<MouvementSoldeRessource> obtenirMouvementsEnAttente(String ressourcesId) {
        return daoAccessorService.getRepository(MouvementSoldeRessourcesRepository.class)
                .findByRessourcesEntity_Id(ressourcesId)
                .stream()
                .filter(m -> "EN_ATTENTE".equals(m.getStatut()))
                .map(m -> dozerMapperBean.map(m, MouvementSoldeRessource.class))
                .collect(Collectors.toList());
    }
}
