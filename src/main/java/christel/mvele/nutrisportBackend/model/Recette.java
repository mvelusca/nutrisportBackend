package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nom;

    private String description;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecetteFavorite> recetteFavorites;

    @ManyToMany(mappedBy = "recettes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;

    @ManyToMany(mappedBy = "recettes", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Aliment> aliments;
}
