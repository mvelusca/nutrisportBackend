package christel.mvele.nutrisportBackend.repository;

import christel.mvele.nutrisportBackend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

//Ce sera AUTO IMPLÉMENTÉ par Spring dans un Bean appelé UtilisateurRepository
// CRUD refers Create, Read, Update, Delete
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByMail(String mail);

    List<Utilisateur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrMailContainingIgnoreCase(String nom, String prenom, String mail);

}
