package cmr.notep.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.MouvementSoldeCaisse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface API pour gérer les transactions de soldes des caisses
 * Les modifications de solde doivent TOUJOURS passer par ces endpoints
 */
@RequestMapping("caisses/transactions")
public interface ICaissesTransactionApi {

    @PostMapping(
            path = "/{idCaisse}/mouvement",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementSoldeCaisse enregistrerMouvement(
            @NonNull @PathVariable(name = "idCaisse") String idCaisse,
            @NonNull @RequestBody MouvementSoldeCaisse mouvement
    ) throws ParcoursException;

    @PutMapping(
            path = "/mouvement/{idMouvement}/valider",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    MouvementSoldeCaisse validerMouvement(
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
            path = "/{idCaisse}/historique",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MouvementSoldeCaisse> obtenirHistorique(
            @NonNull @PathVariable(name = "idCaisse") String idCaisse
    ) throws ParcoursException;

    @GetMapping(
            path = "/{idCaisse}/en-attente",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MouvementSoldeCaisse> obtenirMouvementsEnAttente(
            @NonNull @PathVariable(name = "idCaisse") String idCaisse
    ) throws ParcoursException;
}
