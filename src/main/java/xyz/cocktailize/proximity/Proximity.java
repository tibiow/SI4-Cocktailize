package xyz.cocktailize.proximity;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.lang.reflect.Array;
import java.util.*;

public final class Proximity extends MyWeightedEdge {

    // Needed for graph creation and readability
    private static final String mojito      = "Mojito";

    private static final String mint        = "Mint Mojito";
    private static final String vodka       = "Vodka Mojito";
    private static final String water       = "Water Mojito";
    private static final String milk        = "Milk Mojito";

    // For scenario
    private static final String strawberry  = "Strawberry Mojito";
    private static final String apple       = "Apple Mojito";
    private static final String inferno     = "Inferno Mojito";
    private static final String mojita      = "Mojita";
    private static final String royal       = "Royal Mojito";

    public Proximity() {
    }

    /**
     * Concatenates X SimpleDirectedWeightedGraph to one
     *
     * @param graphList       List of the SimpleDirectedWeightedGraph
     * @param graphWeightList List of the weight of the SimpleDirectedWeightedGraph
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> concat(ArrayList<SimpleDirectedWeightedGraph<String, MyWeightedEdge>> graphList, ArrayList<Double> graphWeightList) {
        ArrayList<String> vertex = new ArrayList<>();
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> ret = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Get all the vertex name from all the given graphs
        for (SimpleDirectedWeightedGraph<String, MyWeightedEdge> actual : graphList) {
            for (String s : actual.vertexSet()) {
                ret.addVertex(s);
                vertex.add(s);
            }
        }

        // Save root
        String src = vertex.get(0);

        // Deleting duplicates + removing the root
        Set<String> set = new HashSet<>();
        set.addAll(vertex);
        set.remove(src);

        // To get the graph weight
        int index = 0;

        // Course of the different graphs
        for (SimpleDirectedWeightedGraph<String, MyWeightedEdge> actual : graphList) {

            double graphWeight = graphWeightList.get(index);

            // Course of the different vertex (except the root)
            for (String dest : set) {
                MyWeightedEdge edge = actual.getEdge(src, dest);

                // If the vertex is present (maybe not all vertex are presents in all graphs)
                if (edge != null) {
                    MyWeightedEdge existingEdge = ret.getEdge(src, dest);

                    // If we already have this vertex on the concatenated graph -> update
                    if (existingEdge != null) {
                        ret.setEdgeWeight(existingEdge, Double.parseDouble(edge.toString()) * graphWeight + Double.parseDouble(existingEdge.toString()));

                        // Else, create this vertex
                    } else {
                        MyWeightedEdge e = ret.addEdge(src, dest);
                        ret.setEdgeWeight(e, Double.parseDouble(edge.toString()) * graphWeight);
                    }

                // The cocktail is set as not close because not present in one graph
                }else{
                    MyWeightedEdge existingEdge = ret.getEdge(src, dest);

                    // If we already have this vertex on the concatenated graph -> update
                    if (existingEdge != null) {
                        ret.setEdgeWeight(existingEdge, Double.parseDouble(existingEdge.toString()) * graphWeight * 10);

                    // Else, create this vertex
                    } else {
                        MyWeightedEdge e = ret.addEdge(src, dest);
                        ret.setEdgeWeight(e, 100 * graphWeight);
                    }
                }
            }
            index++;
        }
        return ret;
    }

    /**
     * Will find the closest cocktail from the one given, based on the associated SimpleDirectedWeightedGraph
     *
     * @param graph    The associated SimpleDirectedWeightedGraph
     * @param cocktail The cocktail we want to find the closest one
     */
    public static String findClosest(SimpleDirectedWeightedGraph<String, MyWeightedEdge> graph, String cocktail) {
        double lowest = 9999999;
        String closest = "";

        // Course of the different vertex
        for (String vertex : graph.vertexSet()) {

            // Except the source one
            if (!cocktail.equals(vertex)) {

                // Get the edge (only one)
                MyWeightedEdge edge = graph.getEdge(cocktail, vertex);
                Double edgeWeight = Double.parseDouble(edge.toString());

                // If this cocktail is closer than the previous one -> update
                if (edgeWeight < lowest) {
                    lowest = edgeWeight;
                    closest = vertex;
                }
            }
        }
        return closest;
    }

    /**
     * Will find the X closest cocktails from the one given, based on the associated SimpleDirectedWeightedGraph
     *
     * @param graph    The associated SimpleDirectedWeightedGraph
     * @param cocktail The cocktail we want to find the closest one
     * @param number   Number of closest cocktails
     */
    public static ArrayList<String> findXClosest(SimpleDirectedWeightedGraph<String, MyWeightedEdge> graph, String cocktail, int number) {

        ArrayList<String> ret   = new ArrayList<>();
        ArrayList<String> name  = new ArrayList<>();
        ArrayList<Double> prox  = new ArrayList<>();

        // Course of the different vertex
        for (String vertex : graph.vertexSet()) {

            // Except the source one
            if (!cocktail.equals(vertex)) {

                // Get the edge (only one)
                MyWeightedEdge edge = graph.getEdge(cocktail, vertex);
                Double edgeWeight = Double.parseDouble(edge.toString());
                prox.add(edgeWeight);
                name.add(vertex);
            }
        }
        ArrayList<Double> sort = (ArrayList<Double>) prox.clone();
        Collections.sort(sort);

        for (int i = 0; i < number; i++){
            for (int j = 0; j < sort.size(); j++){
                if (prox.get(j) == sort.get(0)){
                    ret.add(name.get(j));
                    name.remove(j);
                    prox.remove(j);
                    sort.remove(0);
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Will create an example SimpleDirectedWeightedGraph
     *
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> createMojitoGraph1() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> g = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Add the vertices
        g.addVertex(mojito);
        g.addVertex(mint);
        g.addVertex(vodka);
        g.addVertex(water);

        // Add edges to create linking structure
        MyWeightedEdge e1 = g.addEdge(mojito, mint);
        g.setEdgeWeight(e1, 5);
        MyWeightedEdge e2 = g.addEdge(mojito, vodka);
        g.setEdgeWeight(e2, 10);
        MyWeightedEdge e3 = g.addEdge(mojito, water);
        g.setEdgeWeight(e3, 100);

        return g;
    }

    /**
     * Will create an example SimpleDirectedWeightedGraph
     *
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> createMojitoGraph2() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> g = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Add the vertices
        g.addVertex(mojito);
        g.addVertex(milk);
        g.addVertex(mint);
        g.addVertex(water);

        // Add edges to create linking structure
        MyWeightedEdge e1 = g.addEdge(mojito, milk);
        g.setEdgeWeight(e1, 300);
        MyWeightedEdge e2 = g.addEdge(mojito, mint);
        g.setEdgeWeight(e2, 1000);
        MyWeightedEdge e3 = g.addEdge(mojito, water);
        g.setEdgeWeight(e3, 1);

        return g;
    }

    /**
     * Will create an example SimpleDirectedWeightedGraph
     *
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> createMojitoGraph3() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> g = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Add the vertices
        g.addVertex(mojito);
        g.addVertex(milk);
        g.addVertex(vodka);
        g.addVertex(water);

        // Add edges to create linking structure
        MyWeightedEdge e1 = g.addEdge(mojito, milk);
        g.setEdgeWeight(e1, 50);
        MyWeightedEdge e2 = g.addEdge(mojito, vodka);
        g.setEdgeWeight(e2, 10);
        MyWeightedEdge e3 = g.addEdge(mojito, water);
        g.setEdgeWeight(e3, 100);

        return g;
    }

    /**
     * Will create an example SimpleDirectedWeightedGraph
     *
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> createScenarioGraphExotic() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> g = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Add the vertices
        g.addVertex(mojito);
        g.addVertex(royal);
        g.addVertex(inferno);

        // Add edges to create linking structure
        MyWeightedEdge e1 = g.addEdge(mojito, royal);
        g.setEdgeWeight(e1, 10);
        MyWeightedEdge e2 = g.addEdge(mojito, inferno);
        g.setEdgeWeight(e2, 15);

        return g;
    }

    /**
     * Will create an example SimpleDirectedWeightedGraph
     *
     * @return The created SimpleDirectedWeightedGraph
     */
    public static SimpleDirectedWeightedGraph<String, MyWeightedEdge> createScenarioGraphFreshness() {
        SimpleDirectedWeightedGraph<String, MyWeightedEdge> g = new SimpleDirectedWeightedGraph<>(MyWeightedEdge.class);

        // Add the vertices
        g.addVertex(mojito);
        g.addVertex(mojita);
        g.addVertex(strawberry);
        g.addVertex(apple);

        // Add edges to create linking structure
        MyWeightedEdge e1 = g.addEdge(mojito, mojita);
        g.setEdgeWeight(e1, 10);
        MyWeightedEdge e2 = g.addEdge(mojito, strawberry);
        g.setEdgeWeight(e2, 5);
        MyWeightedEdge e3 = g.addEdge(mojito, apple);
        g.setEdgeWeight(e3, 5);

        return g;
    }
}