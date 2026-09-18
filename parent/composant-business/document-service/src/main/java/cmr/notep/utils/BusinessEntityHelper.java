package cmr.notep.utils;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.DocEtats;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.*;


public class BusinessEntityHelper{

    public static Map<String, Set<DocEtats>> getDocEtatsListToInsert(List<DocEtats> docEtats, List<DocEtats> docEtatsListToInsert ){
        //vérification de la hiérarchie des docEtats
        int maxOrdre = 0;
        docEtatsListToInsert.clear();
        Map<String, Set<DocEtats>> docEtatMapPrecedent = new HashMap<>();
        Map<String, DocEtats> docEtatsList = new HashMap<>();
        Map<String, Set<DocEtats>> docEtatMapBoucle = new HashMap<>();
        for (DocEtats docEtat : docEtats) {
            docEtatsList.putIfAbsent(docEtat.getEtat().getId(), docEtat);
            //reperer les cas de boucles a->b et b->a
            //si dans les listes de mes prédécesseurs, je retrouve un docEtat qui a pour prédécesseur le docEtat que je suis en train de traiter, alors il y a une boucle
            //retirer le docEtat b des prédecesseurs de a et garder docEtat a dans les prédecesseurs de b, pour que JPA puisse référencer correctement les entités
            //une fois l'enregistrement jpa terminé, remettre b dans les prédecesseurs de a et sauvegarder a  pour que la hiérarchie soit correcte
            if(!CollectionUtils.isEmpty(docEtat.getPredecesseurDocEtat())) {
                docEtat.getPredecesseurDocEtat().forEach(predecesseur -> {
                    for(DocEtats docEtat2 : docEtats) {
                        if (trouverSiPredecesseurCyclique(docEtat, predecesseur, docEtat2, docEtatMapBoucle)) break;
                    }
                });
                if(docEtatMapBoucle.containsKey(docEtat.getEtat().getId())) {
                    for(DocEtats predecesseur : docEtatMapBoucle.get(docEtat.getEtat().getId())) {
                        docEtat.getPredecesseurDocEtat().remove(predecesseur);
                    }
                }
            }
            docEtatMapPrecedent.putIfAbsent(docEtat.getEtat().getId(), docEtat.getPredecesseurDocEtat());
        }

        List<String> docEtatsIdListToInsert = searchNextDocEtatWithoutPredecessor(docEtatMapPrecedent,docEtatsList,docEtatsListToInsert);
        removeDocEtatToMapPrecedent(docEtatMapPrecedent, docEtatsIdListToInsert);
        while(!CollectionUtils.isEmpty(docEtatMapPrecedent)) {
            docEtatsIdListToInsert = searchNextDocEtatWithoutPredecessor(docEtatMapPrecedent,docEtatsList,docEtatsListToInsert);
            removeDocEtatToMapPrecedent(docEtatMapPrecedent, docEtatsIdListToInsert);
            maxOrdre++;
            if(maxOrdre > 20) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_OPERATION, "La hiérarchie des docEtats est incorrecte, il y a une boucle dans les prédecesseurs");
            }
        }
        return docEtatMapBoucle;

    }

    private static boolean trouverSiPredecesseurCyclique(DocEtats docEtat, DocEtats predecesseur, DocEtats docEtat2, Map<String, Set<DocEtats>> docEtatMapBoucle) {
        if(predecesseur.getEtat().getId().equalsIgnoreCase(docEtat2.getEtat().getId())) {
            if(!CollectionUtils.isEmpty(docEtat2.getPredecesseurDocEtat())) {
                //on a trouvé un docEtat b qui correspond aux prédécesseur de a. on regarde si celui-ci a pour prédécesseur docEtat a
                for(DocEtats predecesseur2 : docEtat2.getPredecesseurDocEtat()) {
                    if (predecesseur2.getEtat().getId().equalsIgnoreCase(docEtat.getEtat().getId())) {
                        //on a trouvé une boucle a->b et b->a, on supprime b des prédécesseurs de a
                        docEtatMapBoucle.putIfAbsent(docEtat.getEtat().getId(), new HashSet<>());
                        docEtatMapBoucle.get(docEtat.getEtat().getId()).add(docEtat2);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }


    private static void removeDocEtatToMapPrecedent(Map<String, Set<DocEtats>> docEtatMapPrecedent, List<String> docEtatsIdListToInsert) {
        for (String docEtatId : docEtatsIdListToInsert) {
            docEtatMapPrecedent.remove(docEtatId);
            for (Set<DocEtats> predecesseurs : docEtatMapPrecedent.values()) {
                if (predecesseurs == null) {
                    continue;
                }
                predecesseurs.removeIf(predecesseur -> predecesseur.getEtat().getId().equals(docEtatId));
            }
        }
    }

    public static List<String> searchNextDocEtatWithoutPredecessor(Map<String, Set<DocEtats>> docEtatMapPrecedent, Map<String, DocEtats> docEtatsList, List<DocEtats> docEtatsListToInsert){
        List<String> docEtatsIdListToInsert = new ArrayList<>();
        for (Map.Entry<String, Set<DocEtats>> docEtat : docEtatMapPrecedent.entrySet()) {
            if(CollectionUtils.isEmpty(docEtat.getValue())){
                docEtatsIdListToInsert.add(docEtat.getKey());
                docEtatsListToInsert.add(docEtatsList.get(docEtat.getKey()));
            }
        }
        return docEtatsIdListToInsert;
    }

}
