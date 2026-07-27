package cmr.notep.login.gateway;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.login.clientws.PersonnelClientWs;
import cmr.notep.modele.Personnels;
import lombok.extern.slf4j.Slf4j;

/**
 * Implémentation WS de la stratégie Personnel.
 * Utilisée quand parcours.gateway.mode=ws (déploiement distribué).
 */
@Slf4j
public class WsPersonnelGatewayStrategy implements PersonnelGatewayStrategy {

    private final PersonnelClientWs personnelClientWs;

    public WsPersonnelGatewayStrategy(PersonnelClientWs personnelClientWs) {
        this.personnelClientWs = personnelClientWs;
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        log.debug("Récupération personnel par appel WS, id={}", idPersonnel);
        return personnelClientWs.avoirPersonnel(idPersonnel);
    }
}
