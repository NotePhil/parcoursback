package cmr.notep.business;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.modele.DocEtats;
import cmr.notep.repository.DocEtatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@Transactional
public class DocEtatsBusiness {
    private final DaoAccessorService daoAccessorService ;
    private final DozerBeanMapper dozerMapperBean;
    private final DocEtatsRepository docEtatsRepository;

    public DocEtatsBusiness(DaoAccessorService daoAccessorService, DozerBeanMapper dozerMapperBean, DocEtatsRepository docEtatsRepository) {
        this.daoAccessorService = daoAccessorService;
        this.dozerMapperBean = dozerMapperBean;
        this.docEtatsRepository = docEtatsRepository;
    }
    public DocEtats avoirDocEtat(String idDocEtat) {
        return dozerMapperBean.map(this.daoAccessorService.getRepository(DocEtatsRepository.class)
                .findById(idDocEtat)
                .orElseThrow(()-> new RuntimeException("DocEtat introuvable")),DocEtats.class);
    }

    public List<DocEtats> avoirToutDocEtats() {
        return daoAccessorService.getRepository(DocEtatsRepository.class).findAll()
                .stream().map(docEtat -> dozerMapperBean.map(docEtat, DocEtats.class))
                .collect(Collectors.toList());
    }

    public void supprimerDocEtat(DocEtats docEtat) {
        daoAccessorService.getRepository(DocEtatsRepository.class)
                .deleteById(docEtat.getId().toString());
    }

    public DocEtats posterDocEtat(DocEtats docEtat) {
        DocEtatsRepository repo = daoAccessorService.getRepository(DocEtatsRepository.class);
        cmr.notep.dao.DocEtatsEntity entity = dozerMapperBean.map(docEtat, cmr.notep.dao.DocEtatsEntity.class);

        // Recharger les prédécesseurs depuis le repository pour obtenir des entités JPA gérées
        if (entity.getPredecesseursDocEtat() != null && !entity.getPredecesseursDocEtat().isEmpty()) {
            List<cmr.notep.dao.DocEtatsEntity> managedPredecessors = entity.getPredecesseursDocEtat().stream()
                    .map(pred -> repo.findById(pred.getId())
                            .orElseThrow(() -> new RuntimeException("Prédécesseur DocEtat introuvable : " + pred.getId())))
                    .collect(Collectors.toList());
            entity.setPredecesseursDocEtat(managedPredecessors);
        }

        return dozerMapperBean.map(repo.save(entity), DocEtats.class);
    }

    public List<DocEtats> listeDocEtatsParDocument(@NonNull String idDocument) {
        return docEtatsRepository.findByDocuments_Id(idDocument).stream()
                .map(docEtatsEntity -> dozerMapperBean
                        .map(docEtatsEntity, DocEtats.class)).collect(Collectors.toList());
    }

}
