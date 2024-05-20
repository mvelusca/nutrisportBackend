package christel.mvele.nutrisportBackend.repository;

import christel.mvele.nutrisportBackend.model.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//Ce sera AUTO IMPLÉMENTÉ par Spring dans un Bean appelé UtilisateurRepository
// CRUD refers Create, Read, Update, Delete
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByMail(String mail);

}
