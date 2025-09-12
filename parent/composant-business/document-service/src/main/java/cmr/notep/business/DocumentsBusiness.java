package cmr.notep.business;


import static cmr.notep.config.DocumentConfig.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cmr.notep.dao.DocEtatsEntity;
import cmr.notep.dao.EtatsEntity;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Categories;
import cmr.notep.modele.Etats;
import cmr.notep.repository.EtatsRepository;
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

    public Documents assignEtatDoc(Etats etat ,  Documents document)
    {

        log.debug("[assignEtatDoc] assigner un état au document");

        if (etat == null || document == null) {
            throw new IllegalArgumentException("Etat et Document doivent existés");
        }

        EtatsEntity etatEntity = dozerMapperBean.map(
                this.daoAccessorService.getRepository(EtatsRepository.class).findById(etat.getId()),
                EtatsEntity.class);
        DocumentsEntity documentEntity = dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class).findById(document.getIdDocument()),
                DocumentsEntity.class);

        if (etatEntity == null) {
            throw new RuntimeException("Etat non trouvé en base : " + etat.getId());
        }
        if (documentEntity == null) {
            throw new RuntimeException("Document non trouvé en base : " + document.getIdDocument());
        }

        boolean exists = documentEntity.getDocEtatsEntities().stream()
                .anyMatch(de -> de.getEtatsEntity().getId().equals(etatEntity.getId()));
        if (exists) {
            log.debug("[assignEtatDoc] Etat déjà assigné au document");
            return dozerMapperBean.map(documentEntity, Documents.class);
        }

        DocEtatsEntity newDocEtat = new DocEtatsEntity();
        newDocEtat.setDocumentsEntity(documentEntity);
        newDocEtat.setEtatsEntity(etatEntity);
        newDocEtat.setDateCreation(new Date());

        documentEntity.getDocEtatsEntities().add(newDocEtat);

        DocumentsEntity savedDocument = this.daoAccessorService.getRepository(DocumentsRepository.class).save(documentEntity);

        return dozerMapperBean.map(savedDocument, Documents.class);
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
