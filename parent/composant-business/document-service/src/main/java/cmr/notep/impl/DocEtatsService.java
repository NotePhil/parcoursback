package cmr.notep.impl;

import cmr.notep.api.IDocEtatsApi;
import cmr.notep.business.DocEtatsBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.DocEtats;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@Primary
public class DocEtatsService implements IDocEtatsApi {
    private final DocEtatsBusiness docEtatsBusiness;

    public DocEtatsService(DocEtatsBusiness docEtatsBusiness) {
        this.docEtatsBusiness = docEtatsBusiness;
    }

    @Override
    public DocEtats posterDocEtat(DocEtats docEtat) {
        return docEtatsBusiness.posterDocEtat(docEtat);
    }

    @Override
    public DocEtats avoirDocEtat(String idDocEtat) throws ParcoursException {
        return docEtatsBusiness.avoirDocEtat(idDocEtat);
    }

    @Override
    public List<DocEtats> avoirTousDocEtats() {
        return docEtatsBusiness.avoirToutDocEtats();
    }

    @Override
    public List<DocEtats> listeDocEtatsParDocument(@NonNull String idDocument) {
        return docEtatsBusiness.listeDocEtatsParDocument(idDocument);
    }

    @Override
    public void SupprimerDocEtat(DocEtats docEtat) {
        docEtatsBusiness.supprimerDocEtat(docEtat);
    }
}
