package cmr.notep.gateway;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.*;

import java.util.List;

/**
 * Stratégie d'accès aux dépendances métier du service document.
 * Permet de basculer entre appels WS et injection locale sans modifier le code métier.
 */
public interface DocumentGatewayStrategy {

    // Attributs
    Attributs avoirAttribut(String idAttribut) throws ParcoursException;

    // Distributeurs
    Distributeurs avoirDistributeur(String idDistributeur) throws ParcoursException;
    List<Distributeurs> avoirTousDistributeurs() throws ParcoursException;
    Distributeurs posterDistributeur(Distributeurs distributeur) throws ParcoursException;

    // Documents
    Documents avoirDocument(String idDoc) throws ParcoursException;
    List<Documents> avoirTousDocuments() throws ParcoursException;
    Documents posterDocument(Documents document) throws ParcoursException;

    // Etats
    Etats avoirEtat(String idEtat) throws ParcoursException;

    // Personnels
    Personnels avoirPersonnel(String idPersonnel) throws ParcoursException;
    List<Personnels> avoirTousPersonnels() throws ParcoursException;
    Personnels posterPersonnel(Personnels personnel) throws ParcoursException;

    // PrecoMouvements
    PrecoMouvements avoirPrecoMouvement(String idPrecoMouvement) throws ParcoursException;
    List<PrecoMouvements> avoirTousPrecoMouvements() throws ParcoursException;
    PrecoMouvements posterPrecoMouvement(PrecoMouvements precoMouvement) throws ParcoursException;

    // Ressources
    Ressources avoirRessource(String idRessource) throws ParcoursException;

    // Validations
    Validations avoirValidation(String idValidation) throws ParcoursException;

    Personnes avoirPersonne(String personneBeneficiaireId);
}
