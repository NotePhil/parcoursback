package cmr.notep.commun;

import cmr.notep.api.IAttributsApi;
import cmr.notep.api.IDistributeursApi;
import cmr.notep.api.IDocumentsApi;
import cmr.notep.exemplaire.api.IExemplairesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("ittest")
public abstract class AbstractIttest {

  @Autowired
  protected IDocumentsApi documentService;

  @Autowired
  protected IAttributsApi attributService ;
  @Autowired
  protected IExemplairesApi exemplaireService;
  @Autowired
  protected IDistributeursApi distributeurService;
}
