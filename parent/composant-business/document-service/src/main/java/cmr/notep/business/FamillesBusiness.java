package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.FamillesEntity;
import cmr.notep.modele.Familles;
import cmr.notep.repository.FamillesRepository;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

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
}
