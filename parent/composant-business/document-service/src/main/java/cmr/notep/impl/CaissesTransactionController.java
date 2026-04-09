package cmr.notep.impl;

import cmr.notep.api.ICaissesTransactionApi;
import cmr.notep.business.SoldeCaissesTransactionBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.MouvementSoldeCaisse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CaissesTransactionController implements ICaissesTransactionApi {

    private final SoldeCaissesTransactionBusiness soldeCaissesTransactionBusiness;

    public CaissesTransactionController(SoldeCaissesTransactionBusiness soldeCaissesTransactionBusiness) {
        this.soldeCaissesTransactionBusiness = soldeCaissesTransactionBusiness;
    }

    @Override
    public MouvementSoldeCaisse enregistrerMouvement(
            String idCaisse,
            MouvementSoldeCaisse mouvement) throws ParcoursException {
        try {
            return soldeCaissesTransactionBusiness.enregistrerMouvement(
                    idCaisse,
                    mouvement.getSoldeMovementee(),
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
    public MouvementSoldeCaisse validerMouvement(String idMouvement, String valideeBy) throws ParcoursException {
        try {
            return soldeCaissesTransactionBusiness.validerMouvement(idMouvement, valideeBy);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public void rejeterMouvement(String idMouvement, String raison) throws ParcoursException {
        try {
            soldeCaissesTransactionBusiness.rejeterMouvement(idMouvement, raison);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public List<MouvementSoldeCaisse> obtenirHistorique(String idCaisse) throws ParcoursException {
        try {
            return soldeCaissesTransactionBusiness.obtenirHistorique(idCaisse);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }

    @Override
    public List<MouvementSoldeCaisse> obtenirMouvementsEnAttente(String idCaisse) throws ParcoursException {
        try {
            return soldeCaissesTransactionBusiness.obtenirMouvementsEnAttente(idCaisse);
        } catch (RuntimeException e) {
            throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_TRANSACTION, e.getMessage());
        }
    }
}
