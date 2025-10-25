package com.mstproject;

import java.util.*;

public class Graph {
    private final List<String> nodes;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjList;

    public Graph(List<String> nodes) {
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>();
        this.adjList = new HashMap<>();
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }
    }

    public void addEdge(String from, String to, int weight) {
        Edge e = new Edge(from, to, weight);
        edges.add(e);
        adjList.get(from).add(new Edge(from, to, weight));
        adjList.get(to).add(new Edge(to, from, weight)); // undirected
    }

    public List<String> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Map<String, List<Edge>> getAdjList() {
        return adjList;
    }

    public int getVerticesCount() {
        return nodes.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph:\n");
        for (String node : nodes) {
            sb.append(node).append(" -> ").append(adjList.get(node)).append("\n");
        }
        return sb.toString();
    }
}
