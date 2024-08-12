package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(RecetteFavoriteId.class)
public class RecetteFavorite {

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "aliment_id")
    private Aliment aliment;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recette_id")
    private Recette recette;

    private boolean like;
}
