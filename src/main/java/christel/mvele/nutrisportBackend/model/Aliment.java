package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(mappedBy = "aliments")
    @JsonIgnore
    private List<Recette> recettes;
}
