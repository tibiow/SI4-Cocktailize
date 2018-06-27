package xyz.cocktailize.rest;

import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.orm.CocktailDatabase;
import xyz.cocktailize.orm.IngredientDatabase;
import xyz.cocktailize.solver.Solver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("search")
public class SearchService {

    private CocktailDatabase cocktailDatabase = new CocktailDatabase();
    private IngredientDatabase ingredientDatabase = new IngredientDatabase();
    private Solver solver = new Solver();

    @GET
    @Path("/have/ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cocktail> getByIngredients(@QueryParam("i") List<Integer> ingredients) {
        return cocktailDatabase.haveIngredientsById(ingredients);
    }

    @GET
    @Path("/contains/ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cocktail> getById(@QueryParam("i") List<Integer> ids) {
        return cocktailDatabase.containsIngredientsById(ids);
    }

    @GET
    @Path("/optimal/ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public Ingredient getOptimalIngredient(@QueryParam("i") List<Integer> ids) {
        Integer optimal = solver.getOptimalIngredient(cocktailDatabase.containsIngredientsById(ids), ids);
        return ingredientDatabase.getById(optimal);
    }
}
