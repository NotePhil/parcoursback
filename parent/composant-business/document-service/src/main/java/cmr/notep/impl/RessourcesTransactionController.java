package cmr.notep.impl;

import cmr.notep.api.IRessourcesTransactionApi;
import cmr.notep.business.SoldeRessourcesTransactionBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.MouvementSoldeRessource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RessourcesTransactionController implements IRessourcesTransactionApi {

    private final SoldeRessourcesTransactionBusiness soldeRessourcesTransactionBusiness;

    public RessourcesTransactionController(SoldeRessourcesTransactionBusiness soldeRessourcesTransactionBusiness) {
        this.soldeRessourcesTransactionBusiness = soldeRessourcesTransactionBusiness;
    }

    @Override
    public MouvementSoldeRessource enregistrerMouvement(
            String idRessource,
            MouvementSoldeRessource mouvement) throws ParcoursException {
        try {
            return soldeRessourcesTransactionBusiness.enregistrerMouvement(
                    idRessource,
                    mouvement.getQuantiteMouvementee(),
                    mouvement.getTypeTransaction(),
                    mouvement.getDescription(),
                    mouvement.getMotif(),
                    mouvement.getCodeReference(),
                    mouvement.getCreatedBy()
            );
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public MouvementSoldeRessource validerMouvement(String idMouvement, String valideeBy) throws ParcoursException {
        try {
            return soldeRessourcesTransactionBusiness.validerMouvement(idMouvement, valideeBy);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public void rejeterMouvement(String idMouvement, String raison) throws ParcoursException {
        try {
            soldeRessourcesTransactionBusiness.rejeterMouvement(idMouvement, raison);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public List<MouvementSoldeRessource> obtenirHistorique(String idRessource) throws ParcoursException {
        try {
            return soldeRessourcesTransactionBusiness.obtenirHistorique(idRessource);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public List<MouvementSoldeRessource> obtenirMouvementsEnAttente(String idRessource) throws ParcoursException {
        try {
            return soldeRessourcesTransactionBusiness.obtenirMouvementsEnAttente(idRessource);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }
}
