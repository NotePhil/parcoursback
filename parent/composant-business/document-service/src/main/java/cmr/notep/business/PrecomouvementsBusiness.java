package cmr.notep.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.dao.PrecoMouvementsEntity;
import cmr.notep.dao.PrecoMouvementsQtesEntity;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.repository.PrecoMouvementsQtesRepository;
import cmr.notep.repository.PrecoMouvementsRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.dao.DaoAccessorService;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@Transactional
public class PrecomouvementsBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;

    public PrecomouvementsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean) { this.daoAccessorService = daoAccessorService ;
        this.dozerMapperBean = dozerMapperBean;
    }

    public List <PrecoMouvements> avoirTousPrecouvement()
    {
        return daoAccessorService.getRepository(PrecoMouvementsRepository.class).findAll()
                .stream().map(precomouvement -> dozerMapperBean.map(precomouvement,PrecoMouvements.class))
                .collect(Collectors.toList());
    }

    public PrecoMouvements avoirPrecomouvements(String idPreco)
    {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(PrecoMouvementsRepository.class).findById(idPreco)
                .orElseThrow(()->new RuntimeException("Missions non enregistré")), PrecoMouvements.class);
    }

    public PrecoMouvements posterPrecomouvement(PrecoMouvements preco) {
        PrecoMouvementsEntity precoEntity;

        if (preco.getId() != null) {
            precoEntity = this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                    .findById(preco.getId())
                    .orElseThrow(() -> new RuntimeException("PrecoMouvement non trouvé : " + preco.getId()));
            dozerMapperBean.map(preco, precoEntity);
        } else {
            precoEntity = dozerMapperBean.map(preco, PrecoMouvementsEntity.class);
        }

        gererPrecoMouvementsQtes(preco, precoEntity);

        PrecoMouvementsEntity saved = this.daoAccessorService.getRepository(PrecoMouvementsRepository.class).save(precoEntity);
        return dozerMapperBean.map(saved, PrecoMouvements.class);
    }

    private void gererPrecoMouvementsQtes(PrecoMouvements preco, PrecoMouvementsEntity precoEntity) {
        List<PrecoMouvementsQtes> qtesList = preco.getPrecoMouvementsQtes();
        if (qtesList == null) return;

        PrecoMouvementsQtesRepository qtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);

        List<PrecoMouvementsQtesEntity> existingQtes = precoEntity.getPrecoMouvementsQteEntities() != null
                ? new ArrayList<>(precoEntity.getPrecoMouvementsQteEntities())
                : new ArrayList<>();

        List<String> newIds = qtesList.stream()
                .filter(q -> q.getId() != null)
                .map(PrecoMouvementsQtes::getId)
                .collect(Collectors.toList());

        // Supprimer les anciens non présents dans la nouvelle liste
        for (PrecoMouvementsQtesEntity existing : existingQtes) {
            if (!newIds.contains(existing.getId())) {
                qtesRepo.delete(existing);
                precoEntity.getPrecoMouvementsQteEntities().remove(existing);
            }
        }

        if (precoEntity.getPrecoMouvementsQteEntities() == null) {
            precoEntity.setPrecoMouvementsQteEntities(new ArrayList<>());
        }

        // Ajouter ou mettre à jour
        for (PrecoMouvementsQtes qte : qtesList) {
            PrecoMouvementsQtesEntity qteEntity;
            if (qte.getId() != null) {
                qteEntity = qtesRepo.findById(qte.getId())
                        .orElseThrow(() -> new RuntimeException("PrecoMouvementsQte non trouvée : " + qte.getId()));
                dozerMapperBean.map(qte, qteEntity);
            } else {
                qteEntity = dozerMapperBean.map(qte, PrecoMouvementsQtesEntity.class);
                qteEntity.setId(null);
            }
            qteEntity.setPrecoMouvementsEntity(precoEntity);
            PrecoMouvementsQtesEntity savedQte = qtesRepo.save(qteEntity);
            if (!precoEntity.getPrecoMouvementsQteEntities().contains(savedQte)) {
                precoEntity.getPrecoMouvementsQteEntities().add(savedQte);
            }
        }
    }

    public void supprimerPrecomouvement(PrecoMouvements precomouvement) {
        daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .deleteById(precomouvement.getId().toString());
    }

    public PrecoMouvements modifierPrecomouvement(PrecoMouvements precomouvement) {
        // Vérifier que le précomouvement existe avant de le modifier
        this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                .findById(precomouvement.getId().toString())
                .orElseThrow(() -> new RuntimeException("Précomouvement non enregistré"));

        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(PrecoMouvementsRepository.class)
                        .save(dozerMapperBean.map(precomouvement, PrecoMouvementsEntity.class)),
                PrecoMouvements.class);
    }
}
