package cmr.notep.login.impl;

import cmr.notep.exceptions.ParcoursException;
import cmr.notep.login.api.ILoginApi;
import cmr.notep.login.business.LoginBusiness;
import cmr.notep.login.modele.LoginRequest;
import cmr.notep.login.modele.LoginResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class LoginService implements ILoginApi {

    private final LoginBusiness loginBusiness;

    public LoginService(LoginBusiness loginBusiness) {
        this.loginBusiness = loginBusiness;
    }

    @Override
    public LoginResponse authentifier(LoginRequest request) throws ParcoursException {
        return loginBusiness.authentifier(request);
    }
}
