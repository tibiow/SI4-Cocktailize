package xyz.cocktailize.proximity;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by guillaume on 30/05/17.
 * Needed to get the edge Weight
 */
public class MyWeightedEdge extends DefaultWeightedEdge {
    @Override
    public String toString() {
        return Double.toString(getWeight());
    }
}