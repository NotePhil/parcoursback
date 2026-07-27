package cmr.notep.gateway;

import cmr.notep.api.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Implémentation injection locale de la stratégie.
 * Utilisée quand parcours.gateway.mode=injection (par défaut).
 */
@Slf4j
public class InjectedDocumentGatewayStrategy implements DocumentGatewayStrategy {

    private final IAttributsApi attributsApi;
    private final IDistributeursApi distributeursApi;
    private final IDocumentsApi documentsApi;
    private final IEtatsApi etatsApi;
    private final IPersonnelsApi personnelsApi;
    private final IPrecoMouvementsApi precoMouvementsApi;
    private final IRessourcesApi ressourcesApi;
    private final IValidationsApi validationsApi;
    private final IPersonnesApi personnesApi;

    public InjectedDocumentGatewayStrategy(
            IAttributsApi attributsApi,
            IDistributeursApi distributeursApi,
            IDocumentsApi documentsApi,
            IEtatsApi etatsApi,
            IPersonnelsApi personnelsApi,
            IPrecoMouvementsApi precoMouvementsApi,
            IRessourcesApi ressourcesApi,
            IValidationsApi validationsApi, IPersonnesApi personnesApi) {
        this.attributsApi = attributsApi;
        this.distributeursApi = distributeursApi;
        this.documentsApi = documentsApi;
        this.etatsApi = etatsApi;
        this.personnelsApi = personnelsApi;
        this.precoMouvementsApi = precoMouvementsApi;
        this.ressourcesApi = ressourcesApi;
        this.validationsApi = validationsApi;
        this.personnesApi = personnesApi;
    }

    @Override
    public Attributs avoirAttribut(String idAttribut) throws ParcoursException {
        return attributsApi.avoirAttribut(idAttribut);
    }

    @Override
    public Distributeurs avoirDistributeur(String idDistributeur) throws ParcoursException {
        return distributeursApi.avoirDistributeur(idDistributeur);
    }

    @Override
    public List<Distributeurs> avoirTousDistributeurs() throws ParcoursException {
        return distributeursApi.avoirToutDistributeurs();
    }

    @Override
    public Distributeurs posterDistributeur(Distributeurs distributeur) throws ParcoursException {
        return distributeursApi.posterDistributeur(distributeur);
    }

    @Override
    public Documents avoirDocument(String idDoc) throws ParcoursException {
        return documentsApi.avoirDocument(idDoc);
    }

    @Override
    public List<Documents> avoirTousDocuments() throws ParcoursException {
        return documentsApi.avoirTousDocuments();
    }

    @Override
    public Documents posterDocument(Documents document) throws ParcoursException {
        return documentsApi.posterDocument(document);
    }

    @Override
    public Etats avoirEtat(String idEtat) throws ParcoursException {
        return etatsApi.avoirEtat(idEtat);
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        return personnelsApi.avoirPersonnel(idPersonnel);
    }

    @Override
    public List<Personnels> avoirTousPersonnels() throws ParcoursException {
        return personnelsApi.avoirTousPersonnels();
    }

    @Override
    public Personnels posterPersonnel(Personnels personnel) throws ParcoursException {
        return personnelsApi.posterPersonnel(personnel);
    }

    @Override
    public PrecoMouvements avoirPrecoMouvement(String idPrecoMouvement) throws ParcoursException {
        return precoMouvementsApi.avoirPrecoMouvement(idPrecoMouvement);
    }

    @Override
    public List<PrecoMouvements> avoirTousPrecoMouvements() throws ParcoursException {
        return precoMouvementsApi.avoirToutPrecoMouvement();
    }

    @Override
    public PrecoMouvements posterPrecoMouvement(PrecoMouvements precoMouvement) throws ParcoursException {
        return precoMouvementsApi.posterPrecoMouvements(precoMouvement);
    }

    @Override
    public Ressources avoirRessource(String idRessource) throws ParcoursException {
        return ressourcesApi.avoirRessource(idRessource);
    }

    @Override
    public Validations avoirValidation(String idValidation) throws ParcoursException {
        return validationsApi.avoirValidation(idValidation);
    }

    @Override
    public Personnes avoirPersonne(String personneBeneficiaireId) {
        return personnesApi.avoirPersonne(personneBeneficiaireId);
    }
}
