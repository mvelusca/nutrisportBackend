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
public class Aliment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nom;

    @Column(nullable = false)
    private double gras;

    @Column(nullable = false)
    private double proteines;

    @Column(nullable = false)
    private double glucides;

    @OneToMany(mappedBy = "aliment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RecetteFavorite> recetteFavorites;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contenir", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "aliment_id"), // Colonne de jointure pour la table Aliment
            inverseJoinColumns = @JoinColumn(name = "recette_id") // Colonne de jointure pour la table Recette
    )
    private List<Recette> recettes;

    @ManyToMany(mappedBy = "aliments", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;
}
