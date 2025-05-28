package cmr.notep.impl;

import cmr.notep.api.IRessourcesApi;
import cmr.notep.business.RessourcesBusiness;
import cmr.notep.modele.Missions;
import cmr.notep.modele.Ressources;
import cmr.notep.modele.RessourcesRequestBuilder;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Transactional
@Primary
public class RessourcesService implements IRessourcesApi {

    private final RessourcesBusiness ressourcesBusiness;

    public RessourcesService(RessourcesBusiness ressourcesBusiness) {
        this.ressourcesBusiness = ressourcesBusiness;
    }


    @Override
    public Ressources avoirRessource(String idRessource) {
        return ressourcesBusiness.avoirRessource(idRessource);
    }

    @Override
    public List<Ressources> avoirToutRessources() {
        return ressourcesBusiness.avoirToutRessources();
    }

    @Override
    public List<Ressources> triRessources(RessourcesRequestBuilder ressourcesRequestBuilder) {

        String request_sort = ressourcesRequestBuilder.QueryBuilder();

        return ressourcesBusiness.triRessource(request_sort);
    }

    @Override
    public void supprimerRessources(Ressources ressources) {
        ressourcesBusiness.supprimerRessource(ressources);
    }

    @Override
    public Ressources posterRessources(Ressources ressources) {
        return ressourcesBusiness.posterRessource(ressources);
    }
}
