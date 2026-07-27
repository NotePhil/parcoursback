package cmr.notep.config;

import cmr.notep.api.*;
import cmr.notep.clientws.*;
import cmr.notep.gateway.DocumentGatewayStrategy;
import cmr.notep.gateway.InjectedDocumentGatewayStrategy;
import cmr.notep.gateway.WsDocumentGatewayStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration du sélecteur de stratégie d'accès aux dépendances du service document.
 *
 * Propriété: parcours.gateway.mode=injection (défaut) ou ws
 * - injection: appels directs aux interfaces métier (cohérence transactionnelle)
 * - ws: appels HTTP REST vers document-service (déploiement distribué)
 */
@Configuration
public class GatewayStrategyConfig {

    @Value("${parcours.gateway.mode:injection}")
    private String gatewayMode;

    @Bean
    public DocumentGatewayStrategy documentGatewayApi(
            // paramètres mode injection
            IAttributsApi attributsApi,
            IDistributeursApi distributeursApi,
            IDocumentsApi documentsApi,
            IEtatsApi etatsApi,
            IPersonnelsApi personnelsApi,
            IPrecoMouvementsApi precoMouvementsApi,
            IRessourcesApi ressourcesApi,
            IValidationsApi validationsApi,
            IPersonnesApi personnesApi,
            // paramètres mode ws
            AttributsClientWs attributsClientWs,
            DistributeursClientWs distributeursClientWs,
            DocumentsClientWs documentsClientWs,
            EtatsClientWs etatsClientWs,
            PersonnelsClientWs personnelsClientWs,
            PrecoMouvementsClientWs precoMouvementsClientWs,
            RessourcesClientWs ressourcesClientWs,
            ValidationsClientWs validationsClientWs,
            PersonnesClientWs personnesClientWs) {

        if ("ws".equalsIgnoreCase(gatewayMode)) {
            return new WsDocumentGatewayStrategy(
                    attributsClientWs, distributeursClientWs, documentsClientWs,
                    etatsClientWs, personnelsClientWs, precoMouvementsClientWs,
                    ressourcesClientWs, validationsClientWs, personnesClientWs);
        }
        return new InjectedDocumentGatewayStrategy(
                attributsApi, distributeursApi, documentsApi,
                etatsApi, personnelsApi, precoMouvementsApi,
                ressourcesApi, validationsApi, personnesApi);
    }
}
