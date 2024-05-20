package christel.mvele.nutrisportBackend.security;

import christel.mvele.nutrisportBackend.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository repository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        return repository.findByMail(userMail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur inexistant"));
    }
}
