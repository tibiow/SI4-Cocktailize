package xyz.cocktailize.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"name"}))
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    private Set<Ingredient> ingredients = new HashSet<>(0);

    public Cocktail(String name) {
        this.name = name;
    }

    public Cocktail() {
        // Needed for Hibernate
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    @Override
    public String toString() {
        return "xyz.cocktailize.webservices.Cocktail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cocktail cocktail = (Cocktail) o;

        if (id != cocktail.id) return false;
        if (!name.equals(cocktail.name)) return false;
        return ingredients != null ? ingredients.equals(cocktail.ingredients) : cocktail.ingredients == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
}
