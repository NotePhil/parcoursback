package cmr.notep.impl;

import cmr.notep.api.IUtilisateursApi;
import cmr.notep.business.UtilisateursBusiness;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.modele.LoginForm;
import cmr.notep.modele.Utilisateurs;
import cmr.notep.token.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@Primary
public class UtilisateursService implements IUtilisateursApi {

    @Autowired
    private AuthenticationManager authenticationMAnager ;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private final UtilisateursBusiness utilisateursBusiness;

    public UtilisateursService(UtilisateursBusiness utilisateursBusiness) {
        this.utilisateursBusiness = utilisateursBusiness;
    }

    @Override
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

    @Override
    public Utilisateurs avoirUser(String iduser) throws ParcoursException {
        return null;
    }

    @Override
    public List<Utilisateurs> avoirToutUser() {
        return utilisateursBusiness.avoirToutUser();
    }

    @Override
    public void supprimerUser(Utilisateurs user) {

    }

    @Override
    public Utilisateurs posterUser(@NotNull Utilisateurs user) {
        return utilisateursBusiness.posterUser(user);
    }

    @Override
    public Map<String , Object> authenticateAndGetToken(LoginForm loginForm) {

        Authentication authentication =  authenticationMAnager.
                authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(),loginForm.password()));


        if (authentication.isAuthenticated())
        {
            return utilisateursBusiness.UserToken(
                    jwtService.generateToken(userDetailsService.loadUserByUsername(loginForm.username())),
                    loginForm.username()
            );
        }


        else throw  new UsernameNotFoundException("Invalid Credentials") ;
    }

    private String[] getRoles(Utilisateurs user) {
        if (user.getRoles() == null) {
            return new String[]{"USER"};
        }
        return user.getRoles().split(",");
    }
}
