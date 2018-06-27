package xyz.cocktailize;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import xyz.cocktailize.entities.Cocktail;
import xyz.cocktailize.entities.Ingredient;
import xyz.cocktailize.orm.CocktailDatabase;
import xyz.cocktailize.orm.IngredientDatabase;
import xyz.cocktailize.proximity.MyWeightedEdge;
import xyz.cocktailize.proximity.Proximity;
import xyz.cocktailize.solver.Solver;
import xyz.cocktailize.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Demo {

    private Proximity proximity = new Proximity();
    private CocktailDatabase cocktailDatabase = new CocktailDatabase();
    private IngredientDatabase ingredientDatabase = new IngredientDatabase();

    private Ingredient cachaca;
    private Ingredient vokda;
    private Ingredient rhum;
    private Ingredient champagne;
    private Ingredient eauGazeuse;
    private Ingredient citron;
    private Ingredient menthe;
    private Ingredient sucre;
    private Ingredient cranberry;
    private Ingredient ananas;
    private Ingredient coco;
    private Ingredient orange;
    private Ingredient passion;
    private Ingredient grenadine;
    private Ingredient fraise;

    @Before
    public void setUp() throws Exception {
        // Ingredients
        cachaca = new Ingredient("Cachaça");
        vokda = new Ingredient("Vodka");
        rhum = new Ingredient("Rhum");
        champagne = new Ingredient("Champagne");
        eauGazeuse = new Ingredient("Eau gazeuse");

        citron = new Ingredient("Citron");
        menthe = new Ingredient("Menthe");
        sucre = new Ingredient("Sucre");
        cranberry = new Ingredient("Cranberry");
        ananas = new Ingredient("Ananas");
        coco = new Ingredient("Noix de coco");
        orange = new Ingredient("Orange");
        passion = new Ingredient("Fruit de la passion");
        grenadine = new Ingredient("Grenadine");
        fraise = new Ingredient("Fraise");

        // Cocktails
        Cocktail caipirinha = new Cocktail("Caipirinha");
        caipirinha.addIngredient(cachaca);
        caipirinha.addIngredient(citron);
        caipirinha.addIngredient(sucre);
        cocktailDatabase.saveOrUpdate(caipirinha);

        Cocktail cosmopolitan = new Cocktail("Cosmopolitan");
        cosmopolitan.addIngredient(vokda);
        cosmopolitan.addIngredient(cranberry);
        cosmopolitan.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(cosmopolitan);

        Cocktail pinaColada = new Cocktail("Pina Colada");
        pinaColada.addIngredient(rhum);
        pinaColada.addIngredient(ananas);
        pinaColada.addIngredient(coco);
        cocktailDatabase.saveOrUpdate(pinaColada);

        Cocktail virginColada = new Cocktail("Virgin Colada");
        virginColada.addIngredient(orange);
        virginColada.addIngredient(ananas);
        virginColada.addIngredient(coco);
        cocktailDatabase.saveOrUpdate(virginColada);

        Cocktail boraBora = new Cocktail("Bora bora");
        boraBora.addIngredient(ananas);
        boraBora.addIngredient(passion);
        boraBora.addIngredient(grenadine);
        boraBora.addIngredient(citron);
        cocktailDatabase.saveOrUpdate(boraBora);

        Cocktail mojito = new Cocktail("Mojito");
        mojito.addIngredient(rhum);
        mojito.addIngredient(citron);
        mojito.addIngredient(menthe);
        mojito.addIngredient(eauGazeuse);
        cocktailDatabase.saveOrUpdate(mojito);

        Cocktail mojitoFraise = new Cocktail("Mojito fraise");
        mojitoFraise.addIngredient(rhum);
        mojitoFraise.addIngredient(citron);
        mojitoFraise.addIngredient(menthe);
        mojitoFraise.addIngredient(eauGazeuse);
        mojitoFraise.addIngredient(fraise);
        cocktailDatabase.saveOrUpdate(mojitoFraise);

        Cocktail royalMojito = new Cocktail("Royal mojito");
        royalMojito.addIngredient(rhum);
        royalMojito.addIngredient(citron);
        royalMojito.addIngredient(menthe);
        royalMojito.addIngredient(eauGazeuse);
        royalMojito.addIngredient(champagne);
        cocktailDatabase.saveOrUpdate(royalMojito);

        Cocktail mojitoLight = new Cocktail("Mojito Light");
        mojitoLight.addIngredient(rhum);
        mojitoLight.addIngredient(citron);
        mojitoLight.addIngredient(menthe);
        mojitoLight.addIngredient(eauGazeuse);
        mojitoLight.addIngredient(vokda);
        cocktailDatabase.saveOrUpdate(mojitoLight);

        Cocktail mojitoExtra = new Cocktail("Mojito Extra");
        mojitoExtra.addIngredient(rhum);
        mojitoExtra.addIngredient(citron);
        mojitoExtra.addIngredient(menthe);
        mojitoExtra.addIngredient(eauGazeuse);
        mojitoExtra.addIngredient(vokda);
        cocktailDatabase.saveOrUpdate(mojitoExtra);

        Cocktail goldfinger = new Cocktail("Goldfinger");
        goldfinger.addIngredient(rhum);
        goldfinger.addIngredient(citron);
        goldfinger.addIngredient(menthe);
        goldfinger.addIngredient(eauGazeuse);
        goldfinger.addIngredient(orange);
        goldfinger.addIngredient(passion);
        cocktailDatabase.saveOrUpdate(goldfinger);

        Cocktail leviathan = new Cocktail("Leviathan");
        leviathan.addIngredient(rhum);
        leviathan.addIngredient(citron);
        leviathan.addIngredient(menthe);
        leviathan.addIngredient(eauGazeuse);
        leviathan.addIngredient(grenadine);
        leviathan.addIngredient(cranberry);
        leviathan.addIngredient(ananas);
        cocktailDatabase.saveOrUpdate(leviathan);
    }

    @After
    public void tearDown() throws Exception {
        cocktailDatabase.getSession().close();
        HibernateUtil.closeSessionFactory();
    }

    @Test
    public void Scenario1() {

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(rhum);
        ingredients.add(citron);
        ingredients.add(menthe);
        ingredients.add(eauGazeuse);

        System.out.println("Recherche des cocktails que je peux faire avec les ingrédients suivants :");
        ingredients.forEach(System.out::println);

        System.out.println("-> Résultat :");
        List<Cocktail> result = cocktailDatabase.haveIngredients(ingredients);
        System.out.println(result);

        System.out.println("Recherche de l'ingrédient à ajouter pour maximiser le nombre de cocktails réalisables");
        Solver solver = new Solver();
        int optimal = solver.getOptimalIngredient(cocktailDatabase.containsIngredients(ingredients), ingredients.stream().map(Ingredient::getId).collect(Collectors.toList()));
        Ingredient optimalIngredient = ingredientDatabase.getById(optimal);
        System.out.println("-> Ingrédient à ajouter :");
        System.out.println(optimalIngredient);

        System.out.println("-> Cocktails réalisables :");
        ingredients.add(optimalIngredient);
        result.addAll(cocktailDatabase.haveIngredients(ingredients));
        System.out.println(result);

    }

    @Test
    public void Scenario2() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> exotic = proximity.createScenarioGraphExotic();
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> freshness = proximity.createScenarioGraphFreshness();

        ArrayList<SimpleDirectedWeightedGraph<String, MyWeightedEdge>> graphList = new ArrayList<>();
        graphList.add(exotic);
        graphList.add(freshness);

        ArrayList<Double> weightList = new ArrayList<>();
        weightList.add(1.0);
        weightList.add(2.0);

        SimpleDirectedWeightedGraph<String, MyWeightedEdge> concat = proximity.concat(graphList, weightList);

        System.out.println("Proximity graph : \n" + concat);
        System.out.println("We want a cocktail similar to the Mojito, but fresher, and a little more exotic");
        System.out.println("The closest cocktail is the" + proximity.findClosest(concat, "Mojito"));

        System.out.println("We want three cocktails similar to the Mojito, but fresher, and a little more exotic");
        System.out.println("The closest cocktails are " + proximity.findXClosest(concat, "Mojito", 3));
    }

    @Test
    public void Scenario3() {

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(rhum);
        ingredients.add(citron);
        ingredients.add(menthe);
        ingredients.add(eauGazeuse);

        System.out.println("Playlist");

        System.out.println("Ingrédients");
        ingredients.forEach(System.out::println);

        System.out.println();

        System.out.println("Création d'une playlist par proximité d'ingrédients");
        List<Cocktail> cocktails = cocktailDatabase.containsIngredients(ingredients, 10);

        cocktails.forEach(System.out::println);

    }
}
