package cmr.notep.impl;

import cmr.notep.api.IAttributsApi;
import cmr.notep.business.AttributsBusiness;
import cmr.notep.modele.Attributs;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class AttributsService implements IAttributsApi {

    private final AttributsBusiness attribBusiness ;

    public AttributsService(AttributsBusiness attribBusiness) {
        this.attribBusiness = attribBusiness ;
    }

    @Override
    public Attributs avoirAttribut(@NonNull String idAttribut) { return attribBusiness.avoirAttribut(idAttribut);}

    @Override
    public List<Attributs> avoirToutAttribut(){return attribBusiness.avoirToutAttribut();}

    @Override
    public void supprimerAttribut(@NonNull Attributs attrib){
        attribBusiness.supprimerAttribut(attrib);
    }

    @Override
    public Attributs posterAttribut(@NonNull Attributs attributs) {
        return attribBusiness.posterAttribut(attributs);
    }
}
