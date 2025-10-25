package com.mstproject;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject input = gson.fromJson(new FileReader("data/assign_3_input_reset.json"), JsonObject.class);
        JsonArray graphs = input.getAsJsonArray("graphs");
        JsonArray results = new JsonArray();

        System.out.println("🚀 Starting MST computation for " + graphs.size() + " graphs...");
        System.out.println("==============================================================");

        for (JsonElement gElem : graphs) {
            JsonObject g = gElem.getAsJsonObject();
            int id = g.get("id").getAsInt();


            List<String> nodes = new ArrayList<>();
            g.getAsJsonArray("nodes").forEach(n -> nodes.add(n.getAsString()));
            Graph graph = new Graph(nodes);

            g.getAsJsonArray("edges").forEach(e -> {
                JsonObject eo = e.getAsJsonObject();
                graph.addEdge(
                        eo.get("from").getAsString(),
                        eo.get("to").getAsString(),
                        eo.get("weight").getAsInt()
                );
            });

            if (id <= 5) {
                System.out.println("\n=== 🗺 Preview Graph ID: " + id + " ===");
                System.out.println("Vertices (" + graph.getVerticesCount() + "): " + nodes);
                System.out.println("Edges:");
                g.getAsJsonArray("edges").forEach(e -> {
                    JsonObject eo = e.getAsJsonObject();
                    System.out.println("  " + eo.get("from").getAsString() + " - " +
                            eo.get("to").getAsString() + " (w=" + eo.get("weight").getAsInt() + ")");
                });
                System.out.println("===============================");
            }

            System.out.println("\n📊 Processing Graph ID: " + id +
                    " | Vertices: " + graph.getVerticesCount() +
                    " | Edges: " + graph.getEdgesCount());


            long startPrim = System.nanoTime();
            MSTResult prim = PrimAlgorithm.run(graph);
            long endPrim = System.nanoTime();
            prim.setTimeMs((endPrim - startPrim) / 1_000_000.0);

            long startKruskal = System.nanoTime();
            MSTResult kruskal = KruskalAlgorithm.run(graph);
            long endKruskal = System.nanoTime();
            kruskal.setTimeMs((endKruskal - startKruskal) / 1_000_000.0);


            if (prim.getMstEdges().size() < graph.getVerticesCount() - 1)
                prim.setDisconnected(true);
            if (kruskal.getMstEdges().size() < graph.getVerticesCount() - 1)
                kruskal.setDisconnected(true);

            boolean equalCost = prim.getTotalCost() == kruskal.getTotalCost();
            double diffMs = prim.getTimeMs() - kruskal.getTimeMs();
            long diffOps = prim.getOperations() - kruskal.getOperations();
            String fasterAlgorithm;

            if (Math.abs(diffMs) < 1e-6) fasterAlgorithm = "Equal";
            else if (diffMs < 0) fasterAlgorithm = "Prim";
            else fasterAlgorithm = "Kruskal";

            JsonObject result = new JsonObject();
            result.addProperty("graph_id", id);

            JsonObject stats = new JsonObject();
            stats.addProperty("vertices", graph.getVerticesCount());
            stats.addProperty("edges", graph.getEdgesCount());
            result.add("input_stats", stats);

            result.add("prim", toJson(gson, prim));
            result.add("kruskal", toJson(gson, kruskal));


            JsonObject comparison = new JsonObject();
            comparison.addProperty("equal_cost", equalCost);
            comparison.addProperty("faster_algorithm", fasterAlgorithm);
            comparison.addProperty("difference_ms", diffMs);
            comparison.addProperty("difference_ops", diffOps);
            result.add("comparison", comparison);

            results.add(result);


            System.out.printf(
                    "✅ Finished Graph ID: %d | Prim Cost: %d | Kruskal Cost: %d | Faster: %s | Δtime: %.3f ms | Δops: %d%n",
                    id, prim.getTotalCost(), kruskal.getTotalCost(), fasterAlgorithm, diffMs, diffOps
            );
        }


        JsonObject output = new JsonObject();
        output.add("results", results);

        File outputFile = new File("data/output.json");
        outputFile.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(outputFile)) {
            gson.toJson(output, fw);
        }

        System.out.println("\n==============================================================");
        System.out.println("🎯 All graphs processed successfully!");
        System.out.println("📂 Output written to: data/output.json");
        System.out.println("==============================================================");
    }


    private static JsonObject toJson(Gson gson, MSTResult res) {
        JsonObject o = new JsonObject();
        o.add("mst_edges", gson.toJsonTree(res.getMstEdges()));
        o.addProperty("total_cost", res.getTotalCost());
        o.addProperty("operations_count", res.getOperations());
        o.addProperty("execution_time_ms", res.getTimeMs());
        o.addProperty("disconnected", res.isDisconnected());
        return o;
    }
}
