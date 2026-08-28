package cmr.notep.business;


import java.util.*;
import java.util.stream.Collectors;


import cmr.notep.config.mapper.MapperBean;
import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.Categories;
import cmr.notep.modele.Etats;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.repository.DocumentsRepository;
import cmr.notep.repository.EtatsRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cmr.notep.modele.Documents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import static cmr.notep.util.BeanCopyUtils.copyPropertiesExcludingCollections;
import static cmr.notep.util.BeanCopyUtils.copyCollectionsWithDozer;

@Component
@Slf4j
@Transactional
public class DocumentsBusiness {
    private final DaoAccessorService daoAccessorService;
    
    private final CategoriesBusiness categoriesBusiness;
    private final DozerBeanMapper dozerMapperBean;
    //private final BusinessEntityHelper businessEntityHelper; , BusinessEntityHelper businessEntityHelper

    public DocumentsBusiness(DaoAccessorService daoAccessorService, CategoriesBusiness categoriesBusiness, DozerBeanMapper dozerMapperBean) {
        this.daoAccessorService = daoAccessorService;
        this.categoriesBusiness = categoriesBusiness;
        this.dozerMapperBean = dozerMapperBean;
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

        DocumentsRepository documentsRepo = daoAccessorService.getRepository(DocumentsRepository.class);

        DocumentsEntity documentEntity;
        // Si document existe en base -> récupérer l'entité et copier les propriétés scalaires
        if (document.getIdDocument() != null) {
            documentEntity = documentsRepo.findById(document.getIdDocument())
                    .orElseThrow(() -> new ParcoursException(cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.NOT_FOUND, "Document non trouvé : " + document.getIdDocument()));
            // Copier uniquement les propriétés scalaires (exclure collections)
            //copyPropertiesExcludingCollections(document, documentEntity);
            MapperBean.getDozerMapperWithoutList().map(document, documentEntity);
        } else {
            // Nouveau document
            documentEntity = dozerMapperBean.map(document, DocumentsEntity.class);
            // S'assurer que les collections sont vides pour les gérer explicitement
            if (documentEntity.getCategoriesEntities() != null) {
                documentEntity.getCategoriesEntities().clear();
            }
            documentsRepo.save(documentEntity);
            document.setIdDocument(documentEntity.getId());
        }
        
        // Gérer les catégories via le business dédié (synchronisation des entités liées)
        gererCategoriesDocument(document, documentEntity);
        
        // Pour les relations ManyToMany sans attributs (ex: attributs) on laisse JPA gérer directement via le mapping Dozer
        // Mapper le modèle vers l'entité pour que JPA prenne en charge les relations simples
        // Attention : ne pas écraser categoriesEntities déjà synchronisées (gérées via gererCategoriesDocument)
        if(document.getIdDocument() != null){
            copyCollectionsWithDozer(document, documentEntity, dozerMapperBean, "categories");
        }

        DocumentsEntity saved = documentsRepo.save(documentEntity);
        return dozerMapperBean.map(saved, Documents.class);
    }

    // Synchronise les catégories d'un document : ajout/modification/suppression
    private void gererCategoriesDocument(Documents document, DocumentsEntity documentEntity) throws ParcoursException {
        // Récupérer le repository des catégories
        CategoriesRepository categorieRepo = daoAccessorService.getRepository(CategoriesRepository.class);

        // Si la liste fournie est vide -> purge complète des catégories liées
        if (CollectionUtils.isEmpty(document.getCategories())&& !CollectionUtils.isEmpty(documentEntity.getCategoriesEntities())) {
            //TODO loger dans un journal spécifique ces suppressions
            for (CategoriesEntity existing : new ArrayList<>(documentEntity.getCategoriesEntities())) {
                categoriesBusiness.supprimerCategoryEntity(existing, categorieRepo);
            }
            return;
        }
        //Ano on si document présent, alors JPA tente de sauvegarder les catégories du  document sans réussir à gérer cette relation avec attribut
        // Appeler le business Categories pour créer/modifier chaque catégorie fournie
        List<Categories> savedCats = document.getCategories()!=null ? document.getCategories().stream().map(cat -> {
            try {
                cat.setDocument(document);
                return categoriesBusiness.posterCategorie(cat).orElse(null);
            } catch (ParcoursException e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull).collect(Collectors.toList()) : new ArrayList<>();

        // Construire set des ids souhaités
        Set<String> wantedIds = savedCats.stream()
                .map(Categories::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Supprimer les catégories existantes qui ne sont plus désirées
        if (documentEntity.getCategoriesEntities() != null) {
            for (CategoriesEntity existing : new ArrayList<>(documentEntity.getCategoriesEntities())) {
                if (!wantedIds.contains(existing.getId())) {
                    try {
                        categoriesBusiness.supprimerCategoryEntity(existing, categorieRepo);
                        documentEntity.getCategoriesEntities().remove(existing);
                    } catch (Exception e) {
                        throw new ParcoursException(cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum.RELATION_SYNC_FAILED,
                                "Impossible de supprimer la catégorie liée: " + e.getMessage(), e);
                    }
                }
            }
        } else {
            documentEntity.setCategoriesEntities(new ArrayList<>());
        }

        // Mise à jour des relations JPA
        documentEntity.getCategoriesEntities().clear();
        documentEntity.setCategoriesEntities(savedCats.stream()
                .map(cat -> {
                   CategoriesEntity entity = dozerMapperBean.map(cat, CategoriesEntity.class);
                   entity.setDocumentsEntity(documentEntity);
                   return entity;
                })
                .collect(Collectors.toList()));
    }


    public Documents assignEtatDoc(Etats etat ,  Documents document)
    {

        log.debug("[assignEtatDoc] assigner un état au document");

        if (etat == null || document == null) {
            throw new IllegalArgumentException("Etat et Document doivent être non nuls");
        }

        Optional<EtatsEntity> optionalEtatEntity = this.daoAccessorService.getRepository(EtatsRepository.class)
                .findById(etat.getId());
        if (optionalEtatEntity.isEmpty()) {
            throw new RuntimeException("Etat non trouvé en base : " + etat.getId());
        }
        EtatsEntity etatEntity = optionalEtatEntity.get();

        Optional<DocumentsEntity> optionalDocumentEntity = this.daoAccessorService.getRepository(DocumentsRepository.class)
                .findById(document.getIdDocument());
        if (optionalDocumentEntity.isEmpty()) {
            throw new RuntimeException("Document non trouvé en base : " + document.getIdDocument());
        }
        DocumentsEntity documentEntity = optionalDocumentEntity.get();

        boolean exists = documentEntity.getDocEtatsEntities().stream()
                .anyMatch(de -> de.getEtatsEntity().getId().equals(etatEntity.getId()));
        if (exists) {
            log.debug("[assignEtatDoc] Etat déjà assigné au document");
            return dozerMapperBean.map(documentEntity, Documents.class);
        }

        DocEtatsEntity newDocEtat = new DocEtatsEntity();
        newDocEtat.setEtatsEntity(etatEntity);
        newDocEtat.setDocumentsEntity(documentEntity);
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
            List<Categories> categoriesList = document.getCategories().stream()
                    .map(categorie -> {
                        try {
                            log.debug("[modifierDocument] idDocument : " + document.getIdDocument() + " modifier une categorie " + categorie);
                            categorie.setDocument(document);
                            return categoriesBusiness.posterCategorie(categorie).orElse(null);
                        } catch (ParcoursException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            document.getCategories().clear();
            document.getCategories().addAll(categoriesList);
        }
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(DocumentsRepository.class)
                        .save(dozerMapperBean.map(document, DocumentsEntity.class)),
                Documents.class);
    }
}
