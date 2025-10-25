package com.mstproject;

import java.util.List;

public class MSTResult {
    private List<Edge> mstEdges;
    private int totalCost;
    private long ops;
    private double timeMs;
    private boolean disconnected;


    public MSTResult(List<Edge> mstEdges, int totalCost, long ops, double timeMs, boolean disconnected) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.ops = ops;
        this.timeMs = timeMs;
        this.disconnected = disconnected;
    }


    public MSTResult(List<Edge> mstEdges, int totalCost, long ops) {
        this(mstEdges, totalCost, ops, 0.0, false);
    }

    // --- Getters ---
    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public long getOperations() {
        return ops;
    }

    public double getTimeMs() {
        return timeMs;
    }

    public boolean isDisconnected() {
        return disconnected;
    }


    public void setTimeMs(double timeMs) {
        this.timeMs = timeMs;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    @Override
    public String toString() {
        return "MSTResult{" +
                "edges=" + mstEdges.size() +
                ", totalCost=" + totalCost +
                ", operations=" + ops +
                ", timeMs=" + timeMs +
                ", disconnected=" + disconnected +
                '}';
    }
}
