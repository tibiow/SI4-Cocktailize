package xyz.cocktailize.orm;

import org.junit.After;
import org.junit.Test;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.utils.HibernateUtil;

import static org.junit.Assert.assertEquals;

public class IngredientDatabaseTest {

    private IngredientDatabase database = new IngredientDatabase();

    @After
    public void tearDown() throws Exception {
        database.getSession().close();
        HibernateUtil.closeSessionFactory();
    }

    @Test
    public void addIngredient() {
        Ingredient citron = new Ingredient("Citron");
        Ingredient menthe = new Ingredient("Menthe");

        assertEquals(0, citron.getId());
        database.saveOrUpdate(citron);
        assertEquals(1, citron.getId());

        assertEquals(0, menthe.getId());
        database.saveOrUpdate(menthe);
        assertEquals(2, menthe.getId());

        Ingredient citron_db = database.getById(citron.getId());
        assertEquals(citron, citron_db);

        Ingredient menthe_db = database.getById(menthe.getId());
        assertEquals(menthe, menthe_db);

        assertEquals(2, database.count());
    }

}