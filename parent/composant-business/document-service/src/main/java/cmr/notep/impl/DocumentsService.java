package cmr.notep.impl;

import cmr.notep.api.IDocumentsApi;
import cmr.notep.business.DocumentsBusiness;
import cmr.notep.modele.Documents;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class DocumentsService implements IDocumentsApi {

    private final DocumentsBusiness documentsBusiness;
    public DocumentsService(DocumentsBusiness documentsBusiness) {
        Assert.notNull(documentsBusiness,"le composant documentBusiness est obligatoire");
        this.documentsBusiness = documentsBusiness;
    }

    @Override
    public Documents posterDocument(@NonNull  Documents document) {
        return documentsBusiness.posterDocument(document);
    }

    @Override
    public Documents avoirDocument(@NonNull String idDoc) {
        return documentsBusiness.avoirDocument(idDoc);
    }

    @Override
    public List<Documents> avoirTousDocuments() {
        return documentsBusiness.avoirTousDocuments();
    }

    @Override
    public Boolean SupprimerDocument(@NonNull Documents document) {
        return null;
    }
}
