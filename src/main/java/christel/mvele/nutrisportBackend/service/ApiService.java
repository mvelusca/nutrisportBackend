package christel.mvele.nutrisportBackend.service;

import christel.mvele.nutrisportBackend.model.Utilisateur;
import christel.mvele.nutrisportBackend.repository.RoleRepository;
import christel.mvele.nutrisportBackend.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final UtilisateurRepository utilisateurRepository;


    public List<Utilisateur> getAllUsers() {
        List<Utilisateur> users = (List<Utilisateur>) utilisateurRepository.findAll();
        return users.stream()
                .map(user -> Utilisateur.builder()
                        .id(user.getId())
                        .nom(user.getNom())
                        .prenom(user.getPrenom())
                        .mail(user.getMail())
                        .naissance(user.getNaissance())
                        .accountLocked(user.isAccountNonLocked())
                        .roles(user.getRoles())
                        .build())
                .collect(Collectors.toList());
    }


}
