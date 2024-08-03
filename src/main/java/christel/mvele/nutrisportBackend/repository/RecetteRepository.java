package christel.mvele.nutrisportBackend.repository;

import christel.mvele.nutrisportBackend.model.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetteRepository extends JpaRepository<Recette, Integer> {

    Optional<Recette> findByNom(String nom);
}
