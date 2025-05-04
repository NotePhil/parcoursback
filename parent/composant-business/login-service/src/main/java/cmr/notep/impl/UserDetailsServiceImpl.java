package cmr.notep.impl;

import cmr.notep.dao.UtilisateursEntity;
import cmr.notep.repository.UtilisateursRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateursRepository utilisateursRepository;

    public UserDetailsServiceImpl(UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Charger l'utilisateur depuis la base de données
//        UtilisateursEntity utilisateur = (UtilisateursEntity) utilisateursRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
//
//        // Mapper les données de l'utilisateur vers un objet UserDetails
//        return User.builder()
//                .username(utilisateur.getLogin())
//                .password(utilisateur.getMdp()) // Ajouter les rôles (si disponibles)
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UtilisateursEntity utilisateur = (UtilisateursEntity) utilisateursRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        UtilisateursEntity utilisateur = utilisateursRepository.findByLogin(username)
               .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));


        // Retourner un objet UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(utilisateur.getLogin())
                .password(utilisateur.getMdp())
                .build();
    }

}
