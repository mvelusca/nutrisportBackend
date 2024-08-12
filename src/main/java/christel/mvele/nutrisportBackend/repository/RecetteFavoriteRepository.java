package christel.mvele.nutrisportBackend.repository;

import christel.mvele.nutrisportBackend.model.RecetteFavorite;
import christel.mvele.nutrisportBackend.model.RecetteFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetteFavoriteRepository extends JpaRepository<RecetteFavorite, RecetteFavoriteId> {
}
