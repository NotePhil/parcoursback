package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.MouvementSoldeRessource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface API pour gérer les transactions de soldes des ressources
 * Les modifications de solde doivent TOUJOURS passer par ces endpoints
 */
@RequestMapping("ressources/transactions")
public interface IRessourcesTransactionApi {

    @PostMapping(
            path = "/{idRessource}/mouvement",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementSoldeRessource enregistrerMouvement(
            @NonNull @PathVariable(name = "idRessource") String idRessource,
            @NonNull @RequestBody MouvementSoldeRessource mouvement
    ) throws ParcoursException;

    @PutMapping(
            path = "/mouvement/{idMouvement}/valider",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementSoldeRessource validerMouvement(
            @NonNull @PathVariable(name = "idMouvement") String idMouvement,
            @NonNull @RequestParam(name = "valideeBy") String valideeBy
    ) throws ParcoursException;

    @PutMapping(
            path = "/mouvement/{idMouvement}/rejeter",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void rejeterMouvement(
            @NonNull @PathVariable(name = "idMouvement") String idMouvement,
            @NonNull @RequestParam(name = "raison") String raison
    ) throws ParcoursException;

    @GetMapping(
            path = "/{idRessource}/historique",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MouvementSoldeRessource> obtenirHistorique(
            @NonNull @PathVariable(name = "idRessource") String idRessource
    ) throws ParcoursException;

    @GetMapping(
            path = "/{idRessource}/en-attente",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MouvementSoldeRessource> obtenirMouvementsEnAttente(
            @NonNull @PathVariable(name = "idRessource") String idRessource
    ) throws ParcoursException;
}
