package cmr.notep.utils;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.exceptions.enumeration.ParcoursExceptionCodeEnum;
import cmr.notep.modele.DocEtats;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.*;


public class BusinessEntityHelper{

    public static Map<String, Set<DocEtats>> getDocEtatsListToInsert(List<DocEtats> docEtats, List<DocEtats> docEtatsListToInsert ){
        docEtatsListToInsert.clear();
        if (CollectionUtils.isEmpty(docEtats)) {
            return new HashMap<>();
        }

        // Preserve input order so cycle-breaking remains deterministic.
        Map<String, DocEtats> docEtatsParId = new LinkedHashMap<>();
        for (DocEtats docEtat : docEtats) {
            if (docEtat == null || docEtat.getEtat() == null || docEtat.getEtat().getId() == null) {
                continue;
            }
            docEtatsParId.putIfAbsent(docEtat.getEtat().getId(), docEtat);
        }

        Map<String, Set<String>> predecesseursParId = new LinkedHashMap<>();
        for (Map.Entry<String, DocEtats> entry : docEtatsParId.entrySet()) {
            String docEtatId = entry.getKey();
            DocEtats docEtat = entry.getValue();
            Set<String> predecesseurs = new LinkedHashSet<>();
            if (!CollectionUtils.isEmpty(docEtat.getPredecesseurDocEtat())) {
                for (DocEtats predecesseur : docEtat.getPredecesseurDocEtat()) {
                    if (predecesseur == null || predecesseur.getEtat() == null || predecesseur.getEtat().getId() == null) {
                        continue;
                    }
                    String predecesseurId = predecesseur.getEtat().getId();
                    if (docEtatsParId.containsKey(predecesseurId)) {
                        predecesseurs.add(predecesseurId);
                    }
                }
            }
            predecesseursParId.put(docEtatId, predecesseurs);
        }

        Map<String, Set<DocEtats>> docEtatMapBoucle = new HashMap<>();
        int guard = 0;
        int guardMax = Math.max(1, docEtatsParId.size() * docEtatsParId.size());

        while (!predecesseursParId.isEmpty()) {
            List<String> docEtatsIdListToInsert = searchNextDocEtatWithoutPredecessor(predecesseursParId, docEtatsParId, docEtatsListToInsert);

            // If no node is ready, we are in a cycle (including cycles of length > 2): break one edge.
            if (docEtatsIdListToInsert.isEmpty()) {
                String docEtatIdCycle = predecesseursParId.keySet().iterator().next();
                String predecesseurIdCycle = predecesseursParId.get(docEtatIdCycle).iterator().next();

                DocEtats docEtat = docEtatsParId.get(docEtatIdCycle);
                DocEtats predecesseur = docEtatsParId.get(predecesseurIdCycle);

                docEtatMapBoucle.computeIfAbsent(docEtatIdCycle, key -> new HashSet<>()).add(predecesseur);
                predecesseursParId.get(docEtatIdCycle).remove(predecesseurIdCycle);

                if (!CollectionUtils.isEmpty(docEtat.getPredecesseurDocEtat())) {
                    docEtat.getPredecesseurDocEtat().removeIf(pred ->
                            pred != null
                                    && pred.getEtat() != null
                                    && predecesseurIdCycle.equals(pred.getEtat().getId()));
                }
            } else {
                removeDocEtatToMapPrecedent(predecesseursParId, docEtatsIdListToInsert);
            }

            guard++;
            if (guard > guardMax) {
                throw new ParcoursException(ParcoursExceptionCodeEnum.INVALID_OPERATION,
                        "La hiérarchie des docEtats est incorrecte, il y a une boucle dans les predecesseurs");
            }
        }

        return docEtatMapBoucle;

    }


    private static void removeDocEtatToMapPrecedent(Map<String, Set<String>> docEtatMapPrecedent, List<String> docEtatsIdListToInsert) {
        for (String docEtatId : docEtatsIdListToInsert) {
            docEtatMapPrecedent.remove(docEtatId);
            for (Set<String> predecesseurs : docEtatMapPrecedent.values()) {
                if (predecesseurs == null) {
                    continue;
                }
                predecesseurs.remove(docEtatId);
            }
        }
    }

    public static List<String> searchNextDocEtatWithoutPredecessor(Map<String, Set<String>> docEtatMapPrecedent, Map<String, DocEtats> docEtatsList, List<DocEtats> docEtatsListToInsert){
        List<String> docEtatsIdListToInsert = new ArrayList<>();
        for (Map.Entry<String, Set<String>> docEtat : docEtatMapPrecedent.entrySet()) {
            if(CollectionUtils.isEmpty(docEtat.getValue())){
                docEtatsIdListToInsert.add(docEtat.getKey());
                docEtatsListToInsert.add(docEtatsList.get(docEtat.getKey()));
            }
        }
        return docEtatsIdListToInsert;
    }

}
