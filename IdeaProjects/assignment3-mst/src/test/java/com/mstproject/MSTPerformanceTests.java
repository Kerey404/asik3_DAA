package com.mstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

public class MSTPerformanceTests {

    // Проверка, что время выполнения неотрицательное
    private void assertNonNegativeTime(MSTResult result) {
        assertTrue(result.getTimeMs() >= 0, "Execution time must be non-negative");
    }

    // Проверка, что количество операций неотрицательное
    private void assertNonNegativeOps(MSTResult result) {
        assertTrue(result.getOperations() >= 0, "Operation count must be non-negative");
    }

    // Проверка согласованности операций при повторных запусках
    private void assertConsistentOps(MSTResult first, MSTResult second) {
        assertEquals(first.getOperations(), second.getOperations(),
                "Operation counts must be consistent for same dataset");
    }

    @Test
    public void testConnectedGraphPerformance() {
        Graph g = new Graph(Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        // Проверка времени и операций
        assertNonNegativeTime(prim);
        assertNonNegativeTime(kruskal);
        assertNonNegativeOps(prim);
        assertNonNegativeOps(kruskal);
    }

    @Test
    public void testDisconnectedGraphPerformance() {
        Graph g = new Graph(Arrays.asList("A", "B", "C"));
        g.addEdge("A", "B", 5);
        // "C" is isolated

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertTrue(prim.isDisconnected(), "Prim MST should detect disconnected graph");
        assertTrue(kruskal.isDisconnected(), "Kruskal MST should detect disconnected graph");

        // Проверка времени и операций даже для disconnected графа
        assertNonNegativeTime(prim);
        assertNonNegativeTime(kruskal);
        assertNonNegativeOps(prim);
        assertNonNegativeOps(kruskal);
    }

    @Test
    public void testSingleNodeGraphPerformance() {
        Graph g = new Graph(Collections.singletonList("A"));

        MSTResult prim = PrimAlgorithm.run(g);
        MSTResult kruskal = KruskalAlgorithm.run(g);

        assertFalse(prim.isDisconnected(), "Prim MST single node graph should not be disconnected");
        assertFalse(kruskal.isDisconnected(), "Kruskal MST single node graph should not be disconnected");
        assertEquals(0, prim.getMstEdges().size(), "Prim MST edges should be 0");
        assertEquals(0, kruskal.getMstEdges().size(), "Kruskal MST edges should be 0");

        assertNonNegativeTime(prim);
        assertNonNegativeTime(kruskal);
        assertNonNegativeOps(prim);
        assertNonNegativeOps(kruskal);
    }

    @Test
    public void testOperationConsistency() {
        Graph g = new Graph(Arrays.asList("A", "B", "C", "D"));
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 3);
        g.addEdge("A", "D", 4);

        MSTResult prim1 = PrimAlgorithm.run(g);
        MSTResult prim2 = PrimAlgorithm.run(g);
        MSTResult kruskal1 = KruskalAlgorithm.run(g);
        MSTResult kruskal2 = KruskalAlgorithm.run(g);

        // Проверка согласованности операций
        assertConsistentOps(prim1, prim2);
        assertConsistentOps(kruskal1, kruskal2);
    }
}
