package cmr.notep.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.dao.PrecoMouvementsEntity;
import cmr.notep.dao.PrecoMouvementsQtesEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.PrecoMouvements;
import cmr.notep.modele.PrecoMouvementsQtes;
import cmr.notep.repository.PrecoMouvementsQtesRepository;
import cmr.notep.repository.PrecoMouvementsRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public PrecoMouvements posterPrecomouvement(PrecoMouvements preco) throws ParcoursException {
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

    private void gererPrecoMouvementsQtes(PrecoMouvements preco, PrecoMouvementsEntity precoEntity) throws ParcoursException {
        List<PrecoMouvementsQtes> qtesList = preco.getPrecoMouvementsQtes();
        PrecoMouvementsQtesRepository qtesRepo = daoAccessorService.getRepository(PrecoMouvementsQtesRepository.class);

        List<PrecoMouvementsQtesEntity> existingQtes = precoEntity.getPrecoMouvementsQteEntities() != null
                ? new ArrayList<>(precoEntity.getPrecoMouvementsQteEntities())
                : new ArrayList<>();

        // Si liste absente ou vide => purger toutes les quantités
        if (CollectionUtils.isEmpty(qtesList)) {
            try {
                for (PrecoMouvementsQtesEntity existing : existingQtes) {
                    qtesRepo.delete(existing);
                }
                if (precoEntity.getPrecoMouvementsQteEntities() != null) {
                    precoEntity.getPrecoMouvementsQteEntities().clear();
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible de supprimer les quantités du précomouvement: " + e.getMessage(), e);
            }
            return;
        }

        List<String> newIds = qtesList.stream()
                .filter(q -> q.getId() != null)
                .map(PrecoMouvementsQtes::getId)
                .toList();

        // Supprimer les anciens non présents dans la nouvelle liste
        for (PrecoMouvementsQtesEntity existing : existingQtes) {
            if (!newIds.contains(existing.getId())) {
                try {
                    qtesRepo.delete(existing);
                    precoEntity.getPrecoMouvementsQteEntities().remove(existing);
                } catch (Exception e) {
                    throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                            "Impossible de supprimer la quantité " + existing.getId() + ": " + e.getMessage(), e);
                }
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
            try {
                PrecoMouvementsQtesEntity savedQte = qtesRepo.save(qteEntity);
                if (!precoEntity.getPrecoMouvementsQteEntities().contains(savedQte)) {
                    precoEntity.getPrecoMouvementsQteEntities().add(savedQte);
                }
            } catch (Exception e) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                        "Impossible d'enregistrer la quantité du précomouvement: " + e.getMessage(), e);
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
