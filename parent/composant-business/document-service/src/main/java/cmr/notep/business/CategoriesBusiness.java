package cmr.notep.business;

import cmr.notep.dao.*;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.Categories;
import cmr.notep.repository.AssocierRepository;
import cmr.notep.repository.CategoriesRepository;
import cmr.notep.repository.DocumentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cmr.notep.config.DocumentConfig.dozerMapperBean;

@Component
@Slf4j
@Transactional
public class CategoriesBusiness {
    private final DaoAccessorService daoAccessorService;
    public CategoriesBusiness(DaoAccessorService daoAccessorService){
        this.daoAccessorService = daoAccessorService ;
    }

    public Categories avoirCategorie(String id) {
        return dozerMapperBean.map(
                this.daoAccessorService.getRepository(CategoriesRepository.class)
                        .findById(id)
                        .orElseThrow(()->new RuntimeException("Categorie inexistante")), Categories.class);
    }

    public List<Categories> avoirToutCategorie() {
        return daoAccessorService.getRepository(CategoriesRepository.class).findAll()
                .stream().map(cat ->dozerMapperBean.map(cat, Categories.class))
                .collect(Collectors.toList());
    }

    public void supprimerCategory(Categories categories)
    {
        daoAccessorService.getRepository(CategoriesRepository.class)
                .deleteById(categories.getId().toString());
    }

    public Categories posterCategorie(Categories categorie) throws ParcoursException {
        CategoriesEntity categorieEntity = dozerMapperBean.map(categorie, CategoriesEntity.class);
        if(categorieEntity.getId() == null)
           enregistrerNouvelleCategorie(categorieEntity);

        List<AssocierEntity> associerEntities = categorieEntity.getAttributsEntities()
                .stream()
                .map(attributEntity ->{
                    if(attributEntity.getId() == null)
                        if(attributEntity.getAttribut() != null && StringUtils.isNotBlank(attributEntity.getAttribut().getId().toString())
                            && attributEntity.getCategorie() != null && StringUtils.isNotBlank(attributEntity.getCategorie().getId()))
                        {
                            AssocierEntityID associerId = AssocierEntityID.builder()
                                    .categoriesId(attributEntity.getCategorie().getId())
                                    .attributsId(attributEntity.getAttribut().getId().toString())
                                    .build();
                            attributEntity.setId(associerId);
                        }
                    return  this.daoAccessorService.getRepository(AssocierRepository.class)
                        .save(attributEntity);
                }).collect(Collectors.toList());
        categorieEntity.getAttributsEntities().clear();
        categorieEntity.getAttributsEntities().addAll(associerEntities);
        return dozerMapperBean.map( this.daoAccessorService.getRepository(CategoriesRepository.class)
                .save(categorieEntity), Categories.class);
    }

    private void enregistrerNouvelleCategorie(CategoriesEntity categorieEntity) throws ParcoursException {
        log.debug("Enregistrement d'une nouvelle categorie :" + categorieEntity);
        CategoriesEntity newCategorie = new CategoriesEntity();
        //copie manuelle des attributs de categorieEntity dans newCategorie
        //TODO: Utiliser Jackson ou implémenter deepCopy à la place de la copie manuelle
        newCategorie.setLibelle(categorieEntity.getLibelle());
        newCategorie.setOrdre(categorieEntity.getOrdre());
        newCategorie.setDateCreation(categorieEntity.getDateCreation());
        newCategorie.setDateModification(categorieEntity.getDateModification());
        Optional<DocumentsEntity> managedDocument = daoAccessorService.getRepository(DocumentsRepository.class).findById(categorieEntity.getDocumentsEntity().getId());
        if(managedDocument.isPresent())
            newCategorie.setDocumentsEntity(managedDocument.get());
       // List<AssocierEntity> associerEntities = categorieEntity.getAttributsEntities();

       // categorieEntity.getAttributsEntities().clear();
         newCategorie = daoAccessorService.getRepository(CategoriesRepository.class)
                .save(newCategorie);

        categorieEntity.setId(newCategorie.getId());
        log.info("Categorie créée avec succès: {}", categorieEntity.getId());
    }
}
