package xyz.cocktailize.proximity;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by guillaume on 02/06/17.
 */
public class ProximityTest {

    private Proximity                                                       proximity;
    private SimpleDirectedWeightedGraph<String, MyWeightedEdge>             mojitoGraph1;
    private SimpleDirectedWeightedGraph<String, MyWeightedEdge>             mojitoGraph2;
    private SimpleDirectedWeightedGraph<String, MyWeightedEdge>             mojitoGraph3;
    private ArrayList<SimpleDirectedWeightedGraph<String, MyWeightedEdge>>  graphList;
    private ArrayList<Double>                                               weightList;
    private SimpleDirectedWeightedGraph<String, MyWeightedEdge>             concat;

    @Before
    public void init(){
        proximity       = new Proximity();
        mojitoGraph1    = proximity.createMojitoGraph1();
        mojitoGraph2    = proximity.createMojitoGraph2();
        mojitoGraph3    = proximity.createMojitoGraph3();
        graphList       = new ArrayList<>();
        weightList      = new ArrayList<>();
    }

    @Test
    public void Test1Dimension(){

        // Creation of the proximity list
        graphList.add(mojitoGraph1);

        // Creation of the weight list
        weightList.add(2.0);

        // Get the concatenated list
        concat = proximity.concat(graphList, weightList);

        assertEquals("Mint Mojito", proximity.findClosest(concat, "Mojito"));
    }

    @Test
    public void Test2Dimensions(){

        // Creation of the proximity list
        graphList.add(mojitoGraph1);
        graphList.add(mojitoGraph2);

        // Creation of the weight list
        weightList.add(2.0);
        weightList.add(4.0);

        // Get the concatenated list
        concat = proximity.concat(graphList, weightList);

        assertEquals("Water Mojito", proximity.findClosest(concat, "Mojito"));
    }

    @Test
    public void Test3Dimensions(){

        // Creation of the proximity list
        graphList.add(mojitoGraph1);
        graphList.add(mojitoGraph2);
        graphList.add(mojitoGraph3);

        // Creation of the weight list
        weightList.add(2.0);
        weightList.add(4.0);
        weightList.add(100.54);

        // Get the concatenated list
        concat = proximity.concat(graphList, weightList);

        assertEquals("Vodka Mojito", proximity.findClosest(concat, "Mojito"));
    }

    @Test
    public void Test2DimensionsMultipleCocktails(){

        // Creation of the proximity list
        graphList.add(mojitoGraph1);
        graphList.add(mojitoGraph2);
        graphList.add(mojitoGraph3);

        // Creation of the weight list
        weightList.add(2.0);
        weightList.add(4.0);
        weightList.add(100.54);

        // Get the concatenated list
        concat = proximity.concat(graphList, weightList);
        ArrayList<String> res = new ArrayList<>();
        res.add("Vodka Mojito");
        res.add("Milk Mojito");
        res.add("Water Mojito");

        assertEquals(res, proximity.findXClosest(concat, "Mojito", 3));
    }
}
