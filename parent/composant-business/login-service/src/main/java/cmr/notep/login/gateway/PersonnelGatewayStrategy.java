package cmr.notep.login.gateway;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnels;

/**
 * Stratégie d'accès au service Personnel.
 * Permet de basculer entre appels WS et injection locale sans modifier le code métier.
 * Stratégie par défaut : injection locale (parcours.gateway.mode=injection).
 */
public interface PersonnelGatewayStrategy {

    Personnels avoirPersonnel(String idPersonnel) throws ParcoursException;
}
