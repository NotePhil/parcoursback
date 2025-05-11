package cmr.notep.impl;

import cmr.notep.dao.UtilisateursEntity;
import cmr.notep.repository.UtilisateursRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateursRepository utilisateursRepository;

    public UserDetailsServiceImpl(UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UtilisateursEntity utilisateur = utilisateursRepository.findByLogin(username)
               .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        Set<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(utilisateur.getRoles())
                .stream()
                .collect(Collectors.toSet());

        // Retourner un objet UserDetails

        return new org.springframework.security.core.userdetails.User(
                utilisateur.getLogin(),
                utilisateur.getMdp(),
                authorities
        );
    }

}
