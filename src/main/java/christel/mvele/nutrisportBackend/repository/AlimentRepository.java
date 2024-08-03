package christel.mvele.nutrisportBackend.repository;

import christel.mvele.nutrisportBackend.model.Aliment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlimentRepository extends JpaRepository<Aliment, Integer> {

    Optional<Aliment> findByNom(String nom);
}
