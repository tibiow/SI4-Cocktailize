package xyz.cocktailize.solver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.orm.IngredientDatabase;
import xyz.cocktailize.utils.HibernateUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SolverTest {

    private Solver solver;
    private IngredientDatabase database = new IngredientDatabase();

    @Before
    public void setUp() throws Exception {
        solver = new Solver();
    }

    @After
    public void tearDown() throws Exception {
        database.getSession().close();
        HibernateUtil.closeSessionFactory();
    }

    @Test
    public void convertIngredients() throws Exception {

        Ingredient citron = Mockito.mock(Ingredient.class);
        Mockito.when(citron.getId()).thenReturn(1);

        Ingredient menthe = Mockito.mock(Ingredient.class);
        Mockito.when(menthe.getId()).thenReturn(2);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(citron);
        ingredients.add(menthe);

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);

        Set<Integer> ids = solver.convertIngredients(ingredients);
        assertTrue(expected.containsAll(ids));
        assertTrue(ids.containsAll(expected));
    }

    @Test
    public void convertCocktails() throws Exception {

        Ingredient citron = Mockito.mock(Ingredient.class);
        Mockito.when(citron.getId()).thenReturn(1);

        Ingredient menthe = Mockito.mock(Ingredient.class);
        Mockito.when(menthe.getId()).thenReturn(2);

        Ingredient sucre = Mockito.mock(Ingredient.class);
        Mockito.when(sucre.getId()).thenReturn(3);

        Cocktail c1 = new Cocktail("c1");
        c1.addIngredient(citron);
        c1.addIngredient(menthe);

        Cocktail c2 = new Cocktail("c2");
        c2.addIngredient(citron);
        c2.addIngredient(sucre);

        List<Cocktail> cocktails = new ArrayList<>();
        cocktails.add(c1);
        cocktails.add(c2);

        List<Set<Integer>> expected = new ArrayList<>();
        Set<Integer> expected_c1 = new HashSet<>();
        expected_c1.add(1);
        expected_c1.add(2);
        expected.add(expected_c1);
        Set<Integer> expected_c2 = new HashSet<>();
        expected_c2.add(1);
        expected_c2.add(3);
        expected.add(expected_c2);

        List<Set<Integer>> ids = solver.convertCocktails(cocktails);
        assertTrue(expected.containsAll(ids));
        assertTrue(ids.containsAll(expected));
    }

    @Test
    public void getOptimalIngredient() throws Exception {

        Ingredient a = Mockito.mock(Ingredient.class);
        Mockito.when(a.getId()).thenReturn(1);

        Ingredient b = Mockito.mock(Ingredient.class);
        Mockito.when(b.getId()).thenReturn(2);

        Ingredient c = Mockito.mock(Ingredient.class);
        Mockito.when(c.getId()).thenReturn(3);

        Ingredient d = Mockito.mock(Ingredient.class);
        Mockito.when(d.getId()).thenReturn(4);

        Ingredient e = Mockito.mock(Ingredient.class);
        Mockito.when(e.getId()).thenReturn(5);

        Cocktail c1 = new Cocktail("c1");
        c1.addIngredient(a);
        c1.addIngredient(b);

        Cocktail c2 = new Cocktail("c2");
        c2.addIngredient(a);
        c2.addIngredient(b);
        c2.addIngredient(d);
        c2.addIngredient(e);

        Cocktail c3 = new Cocktail("c3");
        c3.addIngredient(a);
        c3.addIngredient(b);
        c3.addIngredient(c);

        Cocktail c4 = new Cocktail("c4");
        c4.addIngredient(b);
        c4.addIngredient(c);

        Cocktail c5 = new Cocktail("c5");
        c5.addIngredient(a);
        c5.addIngredient(c);

        Cocktail c6 = new Cocktail("c6");
        c6.addIngredient(a);
        c6.addIngredient(b);
        c6.addIngredient(d);

        Cocktail c7 = new Cocktail("c7");
        c7.addIngredient(a);
        c7.addIngredient(d);

        List<Cocktail> cocktails = new ArrayList<>();
        cocktails.add(c1);
        cocktails.add(c2);
        cocktails.add(c3);
        cocktails.add(c4);
        cocktails.add(c5);
        cocktails.add(c6);
        cocktails.add(c7);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(a);
        ingredients.add(b);

        int optimal = solver.getOptimalIngredient(cocktails, ingredients.stream().map(Ingredient::getId).collect(Collectors.toList()));
        assertEquals(3, optimal);
    }

}