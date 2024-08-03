package christel.mvele.nutrisportBackend.model;

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

    //une recette a plusieurs aliments
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Aliment> aliments;
}
