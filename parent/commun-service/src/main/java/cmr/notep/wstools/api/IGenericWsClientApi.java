package cmr.notep.wstools.api;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.wstools.modeles.GenericWsRequest;
import cmr.notep.wstools.modeles.GenericWsResponse;

public interface IGenericWsClientApi {
    GenericWsResponse sendRequest(GenericWsRequest request) throws ParcoursException;
}
