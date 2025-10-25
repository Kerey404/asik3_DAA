package com.mstproject;

import java.util.*;

public class KruskalAlgorithm {

    private static class DSU {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> rank = new HashMap<>();
        private int operations = 0;

        DSU(List<String> nodes) {
            for (String n : nodes) {
                parent.put(n, n);
                rank.put(n, 0);
            }
        }

        String find(String x) {
            operations++;
            if (!parent.get(x).equals(x))
                parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }

        boolean union(String a, String b) {
            operations++;
            String pa = find(a);
            String pb = find(b);
            if (pa.equals(pb)) return false;
            if (rank.get(pa) < rank.get(pb)) parent.put(pa, pb);
            else if (rank.get(pa) > rank.get(pb)) parent.put(pb, pa);
            else {
                parent.put(pb, pa);
                rank.put(pa, rank.get(pa) + 1);
            }
            return true;
        }

        int getOperations() { return operations; }
    }

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        List<Edge> mst = new ArrayList<>();
        int total = 0;
        DSU dsu = new DSU(graph.getNodes());

        List<Edge> sorted = new ArrayList<>(graph.getEdges());
        Collections.sort(sorted);

        for (Edge e : sorted) {
            if (dsu.union(e.getFrom(), e.getTo())) {
                mst.add(e);
                total += e.getWeight();
            }
            if (mst.size() == graph.getVerticesCount() - 1) break;
        }

        boolean disconnected = mst.size() < graph.getVerticesCount() - 1;
        if (disconnected) {
            System.out.println("⚠️ Kruskal: Graph is disconnected, MST not complete.");
        }

        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        return new MSTResult(mst, total, dsu.getOperations(), timeMs, disconnected);
    }
}
