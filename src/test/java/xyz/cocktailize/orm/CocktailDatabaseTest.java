package xyz.cocktailize.orm;

import org.junit.After;
import org.junit.Test;
import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CocktailDatabaseTest {

    CocktailDatabase cocktailDatabase = new CocktailDatabase();

    @After
    public void tearDown() throws Exception {
        cocktailDatabase.getSession().close();
        HibernateUtil.closeSessionFactory();
    }

    @Test
    public void addCocktail() {
        Cocktail mojito = new Cocktail("Mojito");
        Cocktail sangria = new Cocktail("Sangria");

        assertEquals(0, mojito.getId());
        cocktailDatabase.saveOrUpdate(mojito);
        assertEquals(1, mojito.getId());

        assertEquals(0, sangria.getId());
        cocktailDatabase.saveOrUpdate(sangria);
        assertEquals(2, sangria.getId());

        Cocktail mojito_db = cocktailDatabase.getById(mojito.getId());
        assertEquals(mojito, mojito_db);

        Cocktail sangria_db = cocktailDatabase.getById(mojito.getId());
        assertEquals(mojito, sangria_db);

        assertEquals(2, cocktailDatabase.count());
    }

    @Test
    public void haveJustTheseIngredients() {
        // Ingredients
        Ingredient rhum = new Ingredient("Rhum");
        Ingredient citron = new Ingredient("Citron");
        Ingredient sucre = new Ingredient("Sucre");

        // Mojito
        Cocktail mojito = new Cocktail("Mojito");
        mojito.addIngredient(rhum);
        mojito.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(mojito);

        // Daïquiri
        Cocktail daiquiri = new Cocktail("Daïquiri");
        daiquiri.addIngredient(rhum);
        daiquiri.addIngredient(sucre);
        cocktailDatabase.saveOrUpdate(daiquiri);

        // Find mojito
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(rhum);
        ingredients.add(citron);

        List<Cocktail> cocktails = cocktailDatabase.haveIngredients(ingredients);
        assertEquals(1, cocktails.size());
        assertEquals(mojito, cocktails.get(0));

        // Find daiquiri
        ingredients = new ArrayList<>();
        ingredients.add(rhum);
        ingredients.add(sucre);

        cocktails = cocktailDatabase.haveIngredients(ingredients);
        assertEquals(1, cocktails.size());
        assertEquals(daiquiri, cocktails.get(0));
    }


    //Exclusive search, we give n ingredients and we got the recipe which contains just the ingredients
    //of the query
    @Test
    public void haveJustTheseIngredientsExclusive() {
        // Ingredients
        Ingredient rhum = new Ingredient("Rhum");
        Ingredient citron = new Ingredient("Citron");
        Ingredient fraise = new Ingredient("Sucre");

        // simple 1
        Cocktail simple1 = new Cocktail("simple1");
        simple1.addIngredient(rhum);
        simple1.addIngredient(citron);
        simple1.addIngredient(fraise);
        cocktailDatabase.saveOrUpdate(simple1);

        // simple 2
        Cocktail simple2 = new Cocktail("simple2");
        simple2.addIngredient(rhum);
        simple2.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(simple2);

        //Ingredient query
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(rhum);
        ingredients.add(citron);

        //Expected answer
        List<Cocktail> cocktailExpected = new ArrayList<>();
        cocktailExpected.add(simple2);

        //Actual answer
        List<Cocktail> cocktailsRes = cocktailDatabase.haveIngredients(ingredients);

        assertEquals(1, cocktailsRes.size());
        assertEquals(cocktailExpected, cocktailsRes);
    }

    //Not exclusive search, we give n ingredients and we got the recipe which contains some of the ingredients
    //of the query
    @Test
    public void haveMoreThanTheseIngredients(){
        // Ingredients
        Ingredient rhum = new Ingredient("Rhum");
        Ingredient citron = new Ingredient("Citron");
        Ingredient fraise = new Ingredient("Sucre");
        Ingredient banane = new Ingredient("Banane");

        // simple 1
        Cocktail simple1 = new Cocktail("simple1");
        simple1.addIngredient(rhum);
        simple1.addIngredient(citron);
        simple1.addIngredient(fraise);
        cocktailDatabase.saveOrUpdate(simple1);

        // simple 2
        Cocktail simple2 = new Cocktail("simple2");
        simple2.addIngredient(rhum);
        simple2.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(simple2);

        // simple 3
        Cocktail simple3 = new Cocktail("simple3");
        simple2.addIngredient(rhum);
        simple2.addIngredient(banane);
        cocktailDatabase.saveOrUpdate(simple3);

        // Ingredient query
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(rhum);
        ingredients.add(citron);

        //Expected answer
        List<Cocktail> cocktailExpected = new ArrayList<>();
        cocktailExpected.add(simple1);
        cocktailExpected.add(simple2);

        //Actual answer
        List<Cocktail> cocktailsRes = cocktailDatabase.containsIngredients(ingredients);

        assertEquals(cocktailExpected.size(), cocktailsRes.size());
        assertTrue(cocktailExpected.containsAll(cocktailsRes));
        assertTrue(cocktailsRes.containsAll(cocktailExpected));
    }

    @Test
    public void haveJustTheseIngredientsLimit() {
        // Ingredients
        Ingredient rhum = new Ingredient("Rhum");
        Ingredient citron = new Ingredient("Citron");
        Ingredient fraise = new Ingredient("Sucre");

        // simple 1
        Cocktail simple1 = new Cocktail("simple1");
        simple1.addIngredient(rhum);
        simple1.addIngredient(citron);
        simple1.addIngredient(fraise);
        cocktailDatabase.saveOrUpdate(simple1);

        // simple 2
        Cocktail simple2 = new Cocktail("simple2");
        simple2.addIngredient(rhum);
        simple2.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(simple2);

        // Ingredient query
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(rhum);
        ingredients.add(citron);

        //Expected answer
        List<Cocktail> cocktailExpected = new ArrayList<>();
        cocktailExpected.add(simple2);

        //Actual answer
        List<Cocktail> cocktailsRes = cocktailDatabase.containsIngredients(ingredients, 1);

        assertEquals(cocktailExpected.size(), cocktailsRes.size());
        assertTrue(cocktailExpected.containsAll(cocktailsRes));
        assertTrue(cocktailsRes.containsAll(cocktailExpected));
    }
}