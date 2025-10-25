package com.mstproject;

import java.util.*;

public class Graph {
    private List<String> nodes;
    private List<Edge> edges;

    public Graph(List<String> nodes) {
        this.nodes = nodes;
        this.edges = new ArrayList<>();
    }

    public void addEdge(String from, String to, int weight) {
        edges.add(new Edge(from, to, weight));
    }

    public List<String> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
    public int getVerticesCount() { return nodes.size(); }
    public int getEdgesCount() { return edges.size(); }

    public Map<String, List<Edge>> getAdjList() {
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String n : nodes) adj.put(n, new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.getFrom()).add(new Edge(e.getFrom(), e.getTo(), e.getWeight()));
            adj.get(e.getTo()).add(new Edge(e.getTo(), e.getFrom(), e.getWeight()));
        }
        return adj;
    }
}
