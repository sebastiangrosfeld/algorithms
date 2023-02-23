package pl.edu.pw.ee.graph;

import java.util.Comparator;

public class EdgeWeightComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge firstEdge, Edge secondEdge) {
        if (firstEdge == null || secondEdge == null) {
            throw new IllegalArgumentException("Edges cannot be null!");
        }

        return Integer.compare(firstEdge.getWeight(), secondEdge.getWeight());
    }

}
