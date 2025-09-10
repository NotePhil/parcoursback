package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.FamillesEntity;
import cmr.notep.modele.Familles;
import cmr.notep.repository.FamillesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class FamillesBusiness {

    private final DaoAccessorService daoAccessorService ;


    public FamillesBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }

    public List<Familles> avoirTousFamilles(){
        return daoAccessorService.getRepository(FamillesRepository.class).findAll()
                .stream().map(famille -> dozerMapperBean.map(famille, Familles.class))
                .collect(Collectors.toList());
    }

    public Familles posterFamille(Familles famille)
    {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(FamillesRepository.class)
                        .save(dozerMapperBean.map(famille, FamillesEntity.class)),
                Familles.class);
    }

    public Familles avoirFamille(String idFamille)
    {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(FamillesRepository.class)
                        .findById(idFamille)
                        .orElseThrow(()->new RuntimeException("Famille non trouvé")),Familles.class);

    }

    public void supprimerFamille(Familles famille) {
        daoAccessorService.getRepository(FamillesRepository.class)
                .deleteById(famille.getId().toString());
    }

    public Familles modifierFamille(Familles famille) {
        // Vérifier que la famille existe avant de la modifier
        this.daoAccessorService.getRepository(FamillesRepository.class)
                .findById(famille.getId().toString())
                .orElseThrow(() -> new RuntimeException("Famille non trouvée"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(FamillesRepository.class)
                        .save(dozerMapperBean.map(famille, FamillesEntity.class)),
                Familles.class);
    }
}
