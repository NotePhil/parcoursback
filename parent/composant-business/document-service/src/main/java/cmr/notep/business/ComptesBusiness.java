package cmr.notep.business;

import cmr.notep.dao.ComptesEntity;
import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Comptes;
import cmr.notep.repository.ComptesRepository;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

public class ComptesBusiness {

    private final DaoAccessorService daoAccessorService ;

    public ComptesBusiness(DaoAccessorService daoAccessorService)
    {
        this.daoAccessorService = daoAccessorService;
    }

    public Comptes avoirCompte(String idCompte) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ComptesRepository.class)
                .findById(idCompte)
                .orElseThrow(()-> new RuntimeException("Compte introuvable")),Comptes.class);
    }

    public List<Comptes> avoirTousComptes() {
        return daoAccessorService.getRepository(ComptesRepository.class).findAll()
                .stream().map(compte -> dozerMapperBean.map(compte,Comptes.class))
                .collect(Collectors.toList());
    }

    public void supprimerCompte(Comptes compte)
    {
        daoAccessorService.getRepository(ComptesRepository.class)
                .deleteById(compte.getId().toString());
    }

    public Comptes posterCompte(Comptes compte) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(ComptesRepository.class)
                .save(dozerMapperBean.map(compte, ComptesEntity.class)), Comptes.class);
    }
}