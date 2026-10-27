package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.exemplaire.repository.MouvementRepository;
import cmr.notep.exemplaire.repository.RessourcesReellesRepository;
import cmr.notep.modele.Mouvements;
import cmr.notep.modele.TypeMouvement;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Service métier pour gérer les transactions de soldes des ressources
 * Les modifications de solde doivent TOUJOURS passer par ce service
 * et ne peuvent pas être modifiées directement
 */
@Component
@Slf4j
@Transactional
public class SoldeRessourcesTransactionBusiness {
    public static final String EN_ATTENTE = "EN_ATTENTE";
    public static final String VALIDEE = "VALIDEE";
    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public SoldeRessourcesTransactionBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    /**
     * Enregistrer une transaction de mouvement pour une ressource
     * @param ressourcesId ID de la ressource
     * @param quantiteMouvementee Détails du mouvement (type, quantité, motif, etc.)
     * @param createdBy Utilisateur qui crée la transaction
     * @return La transaction enregistrée
     */
    public Mouvements enregistrerMouvement(
            String ressourcesId,
            Integer quantiteMouvementee,
            String typeTransaction,
            String description,
            String motif,
            String codeReference,
            String createdBy) {

        RessourcesReellesEntity ressource = daoAccessorService.getRepository(RessourcesReellesRepository.class)
                .findById(ressourcesId)
                .orElseThrow(() -> new ParcoursException(ParcoursExceptionCodeEnum.NOT_FOUND,"Ressource inexistante: " + ressourcesId));

        MouvementsEntity mouvement = new MouvementsEntity();
        mouvement.setRessourceReelleEntity(ressource);
        mouvement.setQuantiteInitiale(ressource.getQuantite());
        mouvement.setQuantiteMouvementee(quantiteMouvementee);

        // Calculer la nouvelle quantité selon le type de transaction
        Integer nouvelleQuantite = ressource.getQuantite();
        if (TypeMouvement.Ajout.name().equalsIgnoreCase(typeTransaction)) {
            nouvelleQuantite += quantiteMouvementee;
        } else if (TypeMouvement.Reduire.name().equalsIgnoreCase(typeTransaction)) {
            if (ressource.getQuantite() < quantiteMouvementee) {
                throw new RuntimeException(
                        "Quantité insuffisante. Disponible: " + ressource.getQuantite() +
                        ", Demandée: " + quantiteMouvementee);
            }
            nouvelleQuantite -= quantiteMouvementee;
        } else if (TypeMouvement.Neutre.name().equalsIgnoreCase(typeTransaction)) {
            nouvelleQuantite = quantiteMouvementee;
        }

        mouvement.setQuantiteFinale(nouvelleQuantite);
        mouvement.setTypeTransaction(typeTransaction);
        mouvement.setDescription(motif + " " + description);
        mouvement.setValideeBy(createdBy);
        mouvement.setDateCreation(new Date());
        mouvement.setStatut(EN_ATTENTE);
        mouvement.setValidee(false);

        MouvementsEntity saved = daoAccessorService
                .getRepository(MouvementRepository.class)
                .save(mouvement);

        log.info("Mouvement de ressource enregistré: {} - Type: {} - Quantité: {}",
                ressourcesId, typeTransaction, quantiteMouvementee);

        return dozerMapperBean.map(saved, Mouvements.class);
    }

    /**
     * Valider et appliquer un mouvement
     */
    @Transactional
    public Mouvements validerMouvement(String mouvementId, String valideeBy) {
        MouvementsEntity mouvement = daoAccessorService
                .getRepository(MouvementRepository.class)
                .findById(mouvementId)
                .orElseThrow(() -> new RuntimeException("Mouvement inexistant: " + mouvementId));

        if (mouvement.getValidee()) {
            throw new RuntimeException("Ce mouvement a déjà été validé");
        }

        // Appliquer la modification à la ressource
        RessourcesReellesEntity ressource = mouvement.getRessourceReelleEntity();
        ressource.setQuantite(mouvement.getQuantiteFinale());
        ressource.setDateModification(new Date());

        daoAccessorService.getRepository(RessourcesReellesRepository.class).save(ressource);

        // Marquer le mouvement comme validé
        mouvement.setValidee(true);
        mouvement.setValideeBy(valideeBy);
        mouvement.setDateValidation(new Date());
        mouvement.setStatut("VALIDEE");

        MouvementsEntity saved = daoAccessorService
                .getRepository(MouvementRepository.class)
                .save(mouvement);

        log.info("Mouvement de ressource validé: {}", mouvementId);
        return dozerMapperBean.map(saved, Mouvements.class);
    }

}
