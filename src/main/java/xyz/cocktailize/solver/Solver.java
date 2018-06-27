package xyz.cocktailize.solver;

import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.orm.IngredientDatabase;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Solver {

    Set<Integer> convertIngredients(Set<Ingredient> ingredients) {
        return ingredients.stream().map(Ingredient::getId).collect(Collectors.toSet());
    }

    List<Set<Integer>> convertCocktails(List<Cocktail> cocktails) {
        List<Set<Integer>> db = new ArrayList<>();
        cocktails.forEach(cocktail -> db.add(convertIngredients(cocktail.getIngredients())));
        return db;
    }

    public Integer getOptimalIngredient(List<Cocktail> cocktails, List<Integer> ingredients) {

        List<Set<Integer>> db = convertCocktails(cocktails);

        JPL.init();
        Query query = new Query("consult", new Atom("prolog/optimal.pl"));
        query.hasSolution();

        String call = "main(";
        call += db.toString() + ",";
        call += ingredients.toString() + ",";
        call += "L).";

        Query request = new Query(call);
        List<Integer> result = new ArrayList<>();
        for (Term term : request.oneSolution().get("L").toTermArray()) {
            result.add(term.arg(1).intValue());
        }

        Integer optimal = result.stream()
                .reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(result, o)))).orElse(null);

        return optimal;
    }

}
