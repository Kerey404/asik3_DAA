package com.mstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class MSTTests {

    @Test
    public void testSmallGraphConnected() {
        Graph g = new Graph(Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertEquals(prim.getTotalCost(), kruskal.getTotalCost());
        assertEquals(g.getVerticesCount() - 1, prim.getMstEdges().size());
        assertFalse(prim.isDisconnected());
    }

    @Test
    public void testDisconnectedGraph() {
        Graph g = new Graph(Arrays.asList("A", "B", "C"));
        g.addEdge("A", "B", 5);
        // "C" is isolated

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertTrue(prim.isDisconnected());
        assertTrue(kruskal.isDisconnected());
    }
}
