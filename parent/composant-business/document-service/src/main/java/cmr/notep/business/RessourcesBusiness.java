package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.RessourcesEntity;
import cmr.notep.modele.Ressources;
import cmr.notep.repository.RessourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class RessourcesBusiness {

    private final DaoAccessorService daoAccessorService;
    private final DozerBeanMapper dozerMapperBean;

    public RessourcesBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
    }

    public Ressources avoirRessource(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(RessourcesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("ressource innexistante inexistante")), Ressources.class);
    }

    public List<Ressources> avoirToutRessources() {
        return daoAccessorService.getRepository(RessourcesRepository.class).findAll()
                .stream().map(ressource ->dozerMapperBean.map(ressource, Ressources.class))
                .collect(Collectors.toList());
    }

    public void supprimerRessource(Ressources ressources)
    {
        daoAccessorService.getRepository(RessourcesRepository.class)
                .deleteById(ressources.getId().toString());
    }

    /**
     * Créer ou mettre à jour une ressource.
     * IMPORTANT : la quantité ne peut pas être modifiée via cette méthode une fois la ressource créée.
     * Toute tentative de modification de la quantité lors d'une mise à jour est ignorée :
     * la valeur existante en base est systématiquement conservée.
     * Pour modifier la quantité, utiliser {@link SoldeRessourcesTransactionBusiness}.
     */
    public Ressources posterRessource(Ressources ressources) {
        RessourcesRepository repository = daoAccessorService.getRepository(RessourcesRepository.class);
        RessourcesEntity entityToSave = dozerMapperBean.map(ressources, RessourcesEntity.class);

        if (ressources.getId() != null && !ressources.getId().isBlank()) {
            RessourcesEntity existing = repository.findById(ressources.getId())
                    .orElseThrow(() -> new RuntimeException("ressource inexistante"));
            // La quantité est protégée : elle ne peut être modifiée que via SoldeRessourcesTransactionBusiness
            entityToSave.setQuantite(existing.getQuantite());
            entityToSave.setDateCreation(existing.getDateCreation());
        }

        return dozerMapperBean.map(repository.save(entityToSave), Ressources.class);
    }

}

