package christel.mvele.nutrisportBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class RecetteFavoriteId implements Serializable {
    private Aliment aliment;
    private Recette recette;

    // Constructeurs, getters, setters, hashCode et equals

    public RecetteFavoriteId() {
    }

    public RecetteFavoriteId(Aliment aliment, Recette recette) {
        this.aliment = aliment;
        this.recette = recette;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetteFavoriteId that = (RecetteFavoriteId) o;
        return Objects.equals(aliment, that.aliment) && Objects.equals(recette, that.recette);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliment, recette);
    }
}