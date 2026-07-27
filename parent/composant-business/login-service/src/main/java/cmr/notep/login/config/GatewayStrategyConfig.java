package cmr.notep.login.config;

import cmr.notep.api.IPersonnelsApi;
import cmr.notep.login.clientws.PersonnelClientWs;
import cmr.notep.login.gateway.InjectedPersonnelGatewayStrategy;
import cmr.notep.login.gateway.PersonnelGatewayStrategy;
import cmr.notep.login.gateway.WsPersonnelGatewayStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sélecteur de stratégie d'accès au service Personnel.
 *
 * Propriété : parcours.gateway.mode=injection (défaut) ou ws
 * - injection : appel direct à IPersonnelsApi (cohérence transactionnelle, mode par défaut)
 * - ws        : appel HTTP REST vers le service document/personnel
 */
@Configuration
public class GatewayStrategyConfig {

    @Value("${parcours.gateway.mode:injection}")
    private String gatewayMode;

    @Bean
    public PersonnelGatewayStrategy personnelGatewayStrategy(
            IPersonnelsApi personnelsApi,
            PersonnelClientWs personnelClientWs) {

        if ("ws".equalsIgnoreCase(gatewayMode)) {
            return new WsPersonnelGatewayStrategy(personnelClientWs);
        }
        return new InjectedPersonnelGatewayStrategy(personnelsApi);
    }
}
