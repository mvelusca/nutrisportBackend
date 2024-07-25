package christel.mvele.nutrisportBackend.service;

import christel.mvele.nutrisportBackend.model.Utilisateur;
import christel.mvele.nutrisportBackend.repository.RoleRepository;
import christel.mvele.nutrisportBackend.repository.TokenRepository;
import christel.mvele.nutrisportBackend.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final UtilisateurRepository utilisateurRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves all users from the database and returns a list of user objects.
     * The returned list contains user objects with only the necessary attributes for display purposes.
     *
     * @return a list of user objects with only the necessary attributes for display purposes
     */
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

    /**
     * Updates an existing user in the database with the provided updated user details.
     *
     * @param id the unique identifier of the user to be updated
     * @param updatedUser the updated user details containing the new values for the user's name, email, and other attributes
     * @return the updated user object after saving it to the database
     * @throws RuntimeException if the user with the given id is not found in the database
     */
    public Utilisateur updateUser(Long id, Utilisateur updatedUser) {
        Optional<Utilisateur> existingUserOptional = utilisateurRepository.findById(updatedUser.getId());
        if (existingUserOptional.isPresent()) {
            Utilisateur existingUser = existingUserOptional.get();
            existingUser.setId(updatedUser.getId());
            existingUser.setNom(updatedUser.getNom());
            existingUser.setMail(updatedUser.getMail());
            return utilisateurRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    /**
     Deletes a user from the database by their id.
     @param id the unique identifier of the user to be deleted
     @throws RuntimeException if the user with the given id is not found in the database
     */
    @Transactional
    public void deleteUser(Integer id) {
        Optional<Utilisateur> existingUserOptional = utilisateurRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            tokenRepository.deleteByUtilisateurId(id);
            utilisateurRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    /**
     * Adds a new user to the database.
     *
     * @param newUser the new user to be added
     * @return the added user object
     * @throws RuntimeException if a user with the same email already exists
     */
    public Utilisateur addUser(Utilisateur newUser) {
        Optional<Utilisateur> existingUserOptional = utilisateurRepository.findByMail(newUser.getMail());
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role User not initialized"));
        if (existingUserOptional.isPresent()) {
            throw new RuntimeException("User with email " + newUser.getMail() + " already exists");
        }else {
            newUser = Utilisateur.builder()
                    .nom(newUser.getNom())
                    .prenom(newUser.getPrenom())
                    .mdp(passwordEncoder.encode(newUser.getMdp()))
                    .mail(newUser.getMail())
                    .photo(newUser.getPhoto())
                    .accountLocked(false)
                    .enabled(true)
                    .roles(List.of(userRole))
                    .createdDate(LocalDate.now())
                    .build();
            return utilisateurRepository.save(newUser);
        }
    }

    public Page<Utilisateur> getUsersPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return utilisateurRepository.findAll(pageable);
    }

    public List<Utilisateur> searchUsers(String query) {
        return utilisateurRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrMailContainingIgnoreCase(query, query, query);
    }

}
