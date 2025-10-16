package cmr.notep.business;


import static cmr.notep.config.DocumentConfig.*;

import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Categories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.dao.DaoAccessorService;
import cmr.notep.dao.DocumentsEntity;
import cmr.notep.modele.Documents;
import cmr.notep.repository.DocumentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
@Transactional
public class DocumentsBusiness {
    private final DaoAccessorService daoAccessorService;
    
    private final CategoriesBusiness categoriesBusiness;
    //private final BusinessEntityHelper businessEntityHelper; , BusinessEntityHelper businessEntityHelper

    public DocumentsBusiness(DaoAccessorService daoAccessorService, CategoriesBusiness categoriesBusiness) {
        this.daoAccessorService = daoAccessorService;
        this.categoriesBusiness = categoriesBusiness;
    }

    public List<Documents> avoirTousDocuments() {
        log.debug("avoirTousDocuments called");
       return daoAccessorService.getRepository(DocumentsRepository.class).findAll()
                .stream().map(doc ->dozerMapperBean.map(doc,Documents.class))
                .collect(Collectors.toList());
    }

    /**
     * Code métier permettant d'enregistrer un nouveau document
     * @param document
     * @return
     */
    public Documents posterDocument(Documents document){
        log.debug("[posterDocument] poster un document");
        if(!CollectionUtils.isEmpty(document.getCategories())) {
            List<Categories> categoriesList = document.getCategories().stream().map(categorie -> {
                try {
                    log.debug("[posterDocument] idDocument : " + document.getIdDocument() + "poster une categorie " + categorie);
                    categorie.setDocument(document);
                    return categoriesBusiness.posterCategorie(categorie);
                } catch (ParcoursException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            document.getCategories().clear();
            document.getCategories().addAll(categoriesList);
        }
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                .save(dozerMapperBean.map(document, DocumentsEntity.class)),
                Documents.class);
    }

    public Documents avoirDocument(String idDoc) {
        log.debug("[avoirDocument] called");
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                        .findById(idDoc)
                        .orElseThrow(()->new RuntimeException("Document non trouvé")),Documents.class);
    }

    public void supprimerDocument(Documents document) {
        log.debug("[supprimerDocument] Suppression du document avec l'ID : " + document.getIdDocument());
        this.daoAccessorService.getRepository(DocumentsRepository.class)
                .deleteById(document.getIdDocument().toString());
    }

    public Documents modifierDocument(Documents document) {
        log.debug("[modifierDocument] Modification du document avec l'ID : " + document.getIdDocument());
        // Vérifier que le document existe avant de le modifier
        this.daoAccessorService.getRepository(DocumentsRepository.class)
                .findById(document.getIdDocument().toString())
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));

        if(!CollectionUtils.isEmpty(document.getCategories())) {
            List<Categories> categoriesList = document.getCategories().stream().map(categorie -> {
                try {
                    log.debug("[modifierDocument] idDocument : " + document.getIdDocument() + " modifier une categorie " + categorie);
                    categorie.setDocument(document);
                    return categoriesBusiness.posterCategorie(categorie);
                } catch (ParcoursException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            document.getCategories().clear();
            document.getCategories().addAll(categoriesList);
        }
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                        .save(dozerMapperBean.map(document, DocumentsEntity.class)),
                Documents.class);
    }
}
