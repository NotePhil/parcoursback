package cmr.notep.utile.tri.modele;

import cmr.notep.modele.Documents;
import cmr.notep.utile.tri.IGenericTri;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;

public class DocumentsTri implements IGenericTri<Documents[]>{
    @Override
    public Documents[] sort(Documents[] documents) {
        Arrays.stream(documents).forEach(document -> {
            if(document.getAttributs()!= null && !document.getAttributs().isEmpty()){
                document.getAttributs().sort(Comparator.comparing(attribut -> attribut.getTitre().toLowerCase()));
            }
        });
        return Arrays.stream(documents).sorted(Comparator.comparing(Documents::getTitre)).toArray(Documents[]::new);
    }
}
