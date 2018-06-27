package xyz.cocktailize.orm;

import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CocktailDatabase extends Database<Cocktail> {

    public CocktailDatabase() {
        super(Cocktail.class);
    }

    public List<Cocktail> haveIngredients(Collection<Ingredient> ingredients) {
        List<Integer> ids = ingredients.stream().map(Ingredient::getId).collect(Collectors.toList());
        return haveIngredientsById(ids);
    }

    //Exclusive
    public List<Cocktail> haveIngredientsById(Collection<Integer> ids) {
        String query = "SELECT c FROM Cocktail c " +
                "JOIN c.ingredients i " +
                "WHERE size(c.ingredients) = :size " +
                "AND i.id IN :ingredients " +
                "GROUP BY c.id " +
                "HAVING COUNT(DISTINCT i.id) = :n";

        return getSession().createQuery(query, Cocktail.class)
                .setParameter("ingredients", ids)
                .setParameter("size", ids.size())
                .setParameter("n", Integer.toUnsignedLong(ids.size()))
                .getResultList();
    }

    public List<Cocktail> containsIngredients(Collection<Ingredient> ingredients, int limit) {
        List<Integer> ids = ingredients.stream().map(Ingredient::getId).collect(Collectors.toList());
        return containsIngredientsById(ids, limit);
    }

    //Have more than the one required
    public List<Cocktail> containsIngredientsById(Collection<Integer> ids, int limit) {
        String query = "SELECT DISTINCT c FROM Cocktail c " +
                "JOIN c.ingredients i " +
                "WHERE i.id IN :ingredients " +
                "GROUP BY c.id " +
                "HAVING COUNT(DISTINCT i.id) = :n " +
                "ORDER BY size(c.ingredients) ASC";

        return getSession().createQuery(query, Cocktail.class)
                .setParameter("ingredients", ids)
                .setParameter("n", Integer.toUnsignedLong(ids.size()))
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Cocktail> containsIngredients(Collection<Ingredient> ingredients) {
        List<Integer> ids = ingredients.stream().map(Ingredient::getId).collect(Collectors.toList());
        return containsIngredientsById(ids);
    }

    public List<Cocktail> containsIngredientsById(Collection<Integer> ids) {
        return containsIngredientsById(ids, -1);
    }
}
