package com.mstproject;

import java.util.*;

public class PrimAlgorithm {

    public static MSTResult run(Graph graph) {
        long start = System.nanoTime();
        Map<String, List<Edge>> adj = graph.getAdjList();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();
        int total = 0;
        long ops = 0;

        String startNode = graph.getNodes().get(0);
        visited.add(startNode);
        pq.addAll(adj.get(startNode));

        while (!pq.isEmpty() && mst.size() < graph.getVerticesCount() - 1) {
            Edge e = pq.poll();
            ops++;
            if (visited.contains(e.getTo())) continue;

            visited.add(e.getTo());
            mst.add(e);
            total += e.getWeight();

            for (Edge next : adj.get(e.getTo())) {
                if (!visited.contains(next.getTo())) pq.add(next);
            }
        }

        boolean disconnected = mst.size() < graph.getVerticesCount() - 1;
        if (disconnected) {
            System.out.println("⚠️ Prim: Graph is disconnected, MST not complete.");
        }

        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        return new MSTResult(mst, total, ops, timeMs, disconnected);
    }
}
