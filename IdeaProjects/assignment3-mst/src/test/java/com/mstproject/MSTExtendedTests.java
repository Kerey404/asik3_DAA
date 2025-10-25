package com.mstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MSTExtendedTests {

    private boolean isAcyclic(List<Edge> edges, List<String> nodes) {
        Map<String, String> parent = new HashMap<>();
        for (String node : nodes) parent.put(node, node);

        for (Edge e : edges) {
            String a = find(parent, e.getFrom());
            String b = find(parent, e.getTo());
            if (a.equals(b)) return false;
            parent.put(a, b);
        }
        return true;
    }

    private String find(Map<String, String> parent, String x) {
        if (!parent.get(x).equals(x))
            parent.put(x, find(parent, parent.get(x)));
        return parent.get(x);
    }

    // Проверка, что все вершины встречаются в MST
    private boolean allVerticesConnected(List<Edge> edges, List<String> nodes) {
        Set<String> visited = new HashSet<>();
        for (Edge e : edges) {
            visited.add(e.getFrom());
            visited.add(e.getTo());
        }
        return visited.containsAll(nodes);
    }

    @Test
    public void testConnectedGraph() {
        Graph g = new Graph(Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertEquals(prim.getTotalCost(), kruskal.getTotalCost());

        assertEquals(g.getVerticesCount() - 1, prim.getMstEdges().size());
        assertEquals(g.getVerticesCount() - 1, kruskal.getMstEdges().size());

        // MST acyclic
        assertTrue(isAcyclic(prim.getMstEdges(), g.getNodes()));
        assertTrue(isAcyclic(kruskal.getMstEdges(), g.getNodes()));

        // Все вершины соединены
        assertTrue(allVerticesConnected(prim.getMstEdges(), g.getNodes()));
        assertTrue(allVerticesConnected(kruskal.getMstEdges(), g.getNodes()));

        // Не disconnected
        assertFalse(prim.isDisconnected());
        assertFalse(kruskal.isDisconnected());
    }


    @Test
    public void testReproducibility() {
        Graph g = new Graph(Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);

        MSTResult prim1 = PrimAlgorithm.run(g);
        MSTResult prim2 = PrimAlgorithm.run(g);
        assertEquals(prim1.getTotalCost(), prim2.getTotalCost());
        assertEquals(prim1.getMstEdges().size(), prim2.getMstEdges().size());
    }


    // граф с одной вершиной
    @Test
    public void testSingleNodeGraph() {
        Graph g = new Graph(Collections.singletonList("A"));

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertFalse(prim.isDisconnected());
        assertFalse(kruskal.isDisconnected());
        assertEquals(0, prim.getMstEdges().size());
        assertEquals(0, kruskal.getMstEdges().size());
    }

    // одинаковые веса рёбер
    @Test
    public void testEqualWeightEdges() {
        Graph g = new Graph(Arrays.asList("A", "B", "C"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 1);
        g.addEdge("A", "C", 1);

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertEquals(2, prim.getMstEdges().size());
        assertEquals(2, kruskal.getMstEdges().size());
        assertEquals(prim.getTotalCost(), kruskal.getTotalCost());
        assertTrue(isAcyclic(prim.getMstEdges(), g.getNodes()));
        assertTrue(allVerticesConnected(prim.getMstEdges(), g.getNodes()));
    }
}
