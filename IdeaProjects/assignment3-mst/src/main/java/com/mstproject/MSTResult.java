package com.mstproject;

import java.util.List;

public class MSTResult {
    private List<Edge> mstEdges;
    private int totalCost;
    private long operations;
    private double timeMs;
    private boolean disconnected;

    public MSTResult(List<Edge> mstEdges, int totalCost, long operations, double timeMs, boolean disconnected) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operations = operations;
        this.timeMs = timeMs;
        this.disconnected = disconnected;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public long getOperations() { return operations; }
    public double getTimeMs() { return timeMs; }
    public boolean isDisconnected() { return disconnected; }
}
