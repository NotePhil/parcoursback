package cmr.notep.login.gateway;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Personnels;
import lombok.extern.slf4j.Slf4j;

/**
 * Implémentation locale de la stratégie Personnel.
 * Utilisée quand parcours.gateway.mode=injection (défaut).
 * Appel direct à l'interface IPersonnelsApi injectée par Spring.
 */
@Slf4j
public class InjectedPersonnelGatewayStrategy implements PersonnelGatewayStrategy {

    private final IPersonnelsApi personnelsApi;

    public InjectedPersonnelGatewayStrategy(IPersonnelsApi personnelsApi) {
        this.personnelsApi = personnelsApi;
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        log.debug("Récupération personnel par injection locale, id={}", idPersonnel);
        return personnelsApi.avoirPersonnel(idPersonnel);
    }
}
