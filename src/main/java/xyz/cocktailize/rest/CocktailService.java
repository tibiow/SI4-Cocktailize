package xyz.cocktailize.rest;

import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.orm.CocktailDatabase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("cocktail")
public class CocktailService {

    private CocktailDatabase database = new CocktailDatabase();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cocktail addCocktail(Cocktail cocktail) {
        database.saveOrUpdate(cocktail);
        return cocktail;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cocktail getById(@PathParam("id") Integer id) {
        return database.getById(id);
    }
}
