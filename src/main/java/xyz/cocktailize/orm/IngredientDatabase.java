package xyz.cocktailize.orm;

import xyz.cocktailize.entities.Ingredient;

public class IngredientDatabase extends Database<Ingredient> {

    public IngredientDatabase() {
        super(Ingredient.class);
    }

}
