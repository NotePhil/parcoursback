package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.Etats;
import cmr.notep.repository.EtatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class EtatsBusiness {
    private final DaoAccessorService daoAccessorService ;

    public EtatsBusiness(DaoAccessorService daoAccessorService) {
        this.daoAccessorService = daoAccessorService;
    }
    public Etats avoirEtat(String idEtat) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(EtatsRepository.class)
                .findById(idEtat)
                .orElseThrow(()-> new RuntimeException("Etat introuvable")),Etats.class);
    }

    public List<Etats> avoirToutEtats() {
        return daoAccessorService.getRepository(EtatsRepository.class).findAll()
                .stream().map(etat -> dozerMapperBean.map(etat, Etats.class))
                .collect(Collectors.toList());
    }

    public void supprimerEtat(Etats etat) {
        daoAccessorService.getRepository(EtatsRepository.class)
                .deleteById(etat.getId().toString());
    }

    public Etats posterEtat(Etats etat) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(EtatsRepository.class)
                        .save(dozerMapperBean.map(etat, cmr.notep.dao.EtatsEntity.class)),
                Etats.class);
    }

    public Etats modifierEtat(Etats etat) {
        // Vérifier que l'état existe avant de le modifier
        this.daoAccessorService.getRepository(EtatsRepository.class)
                .findById(etat.getId().toString())
                .orElseThrow(() -> new RuntimeException("Etat introuvable"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(EtatsRepository.class)
                        .save(dozerMapperBean.map(etat, cmr.notep.dao.EtatsEntity.class)),
                Etats.class);
    }
}
