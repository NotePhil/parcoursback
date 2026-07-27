package cmr.notep.gateway;

import cmr.notep.clientws.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Implémentation WS de la stratégie.
 * Utilisée quand parcours.gateway.mode=ws (déploiement distribué).
 */
@Slf4j
public class WsDocumentGatewayStrategy implements DocumentGatewayStrategy {

    private final AttributsClientWs attributsClientWs;
    private final DistributeursClientWs distributeursClientWs;
    private final DocumentsClientWs documentsClientWs;
    private final EtatsClientWs etatsClientWs;
    private final PersonnelsClientWs personnelsClientWs;
    private final PrecoMouvementsClientWs precoMouvementsClientWs;
    private final RessourcesClientWs ressourcesClientWs;
    private final ValidationsClientWs validationsClientWs;
    private final PersonnesClientWs personnesClientWs;

    public WsDocumentGatewayStrategy(
            AttributsClientWs attributsClientWs,
            DistributeursClientWs distributeursClientWs,
            DocumentsClientWs documentsClientWs,
            EtatsClientWs etatsClientWs,
            PersonnelsClientWs personnelsClientWs,
            PrecoMouvementsClientWs precoMouvementsClientWs,
            RessourcesClientWs ressourcesClientWs,
            ValidationsClientWs validationsClientWs, PersonnesClientWs personnesClientWs) {
        this.attributsClientWs = attributsClientWs;
        this.distributeursClientWs = distributeursClientWs;
        this.documentsClientWs = documentsClientWs;
        this.etatsClientWs = etatsClientWs;
        this.personnelsClientWs = personnelsClientWs;
        this.precoMouvementsClientWs = precoMouvementsClientWs;
        this.ressourcesClientWs = ressourcesClientWs;
        this.validationsClientWs = validationsClientWs;
        this.personnesClientWs = personnesClientWs;
    }

    @Override
    public Attributs avoirAttribut(String idAttribut) throws ParcoursException {
        return attributsClientWs.avoirAttribut(idAttribut);
    }

    @Override
    public Distributeurs avoirDistributeur(String idDistributeur) throws ParcoursException {
        return distributeursClientWs.avoirDistributeur(idDistributeur);
    }

    @Override
    public List<Distributeurs> avoirTousDistributeurs() throws ParcoursException {
        return distributeursClientWs.avoirToutDistributeurs();
    }

    @Override
    public Distributeurs posterDistributeur(Distributeurs distributeur) throws ParcoursException {
        return distributeursClientWs.posterDistributeur(distributeur);
    }

    @Override
    public Documents avoirDocument(String idDoc) throws ParcoursException {
        return documentsClientWs.avoirDocument(idDoc);
    }

    @Override
    public List<Documents> avoirTousDocuments() throws ParcoursException {
        return documentsClientWs.avoirTousDocuments();
    }

    @Override
    public Documents posterDocument(Documents document) throws ParcoursException {
        return documentsClientWs.posterDocument(document);
    }

    @Override
    public Etats avoirEtat(String idEtat) throws ParcoursException {
        return etatsClientWs.avoirEtat(idEtat);
    }

    @Override
    public Personnels avoirPersonnel(String idPersonnel) throws ParcoursException {
        return personnelsClientWs.avoirPersonnel(idPersonnel);
    }

    @Override
    public List<Personnels> avoirTousPersonnels() throws ParcoursException {
        return personnelsClientWs.avoirTousPersonnels();
    }

    @Override
    public Personnels posterPersonnel(Personnels personnel) throws ParcoursException {
        return personnelsClientWs.posterPersonnel(personnel);
    }

    @Override
    public PrecoMouvements avoirPrecoMouvement(String idPrecoMouvement) throws ParcoursException {
        return precoMouvementsClientWs.avoirPrecoMouvement(idPrecoMouvement);
    }

    @Override
    public List<PrecoMouvements> avoirTousPrecoMouvements() throws ParcoursException {
        return precoMouvementsClientWs.avoirToutPrecoMouvement();
    }

    @Override
    public PrecoMouvements posterPrecoMouvement(PrecoMouvements precoMouvement) throws ParcoursException {
        return precoMouvementsClientWs.posterPrecoMouvements(precoMouvement);
    }

    @Override
    public Ressources avoirRessource(String idRessource) throws ParcoursException {
        return ressourcesClientWs.avoirRessource(idRessource);
    }

    @Override
    public Validations avoirValidation(String idValidation) throws ParcoursException {
        return validationsClientWs.avoirValidation(idValidation);
    }

    @Override
    public Personnes avoirPersonne(String personneBeneficiaireId) {
        return personnesClientWs.avoirPersonne(personneBeneficiaireId);
    }
}
