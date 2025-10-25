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

        for (JsonElement gElem : graphs) {
            JsonObject g = gElem.getAsJsonObject();
            int id = g.get("id").getAsInt();
            List<String> nodes = new ArrayList<>();
            g.getAsJsonArray("nodes").forEach(n -> nodes.add(n.getAsString()));
            Graph graph = new Graph(nodes);

            g.getAsJsonArray("edges").forEach(e -> {
                JsonObject eo = e.getAsJsonObject();
                graph.addEdge(eo.get("from").getAsString(), eo.get("to").getAsString(), eo.get("weight").getAsInt());
            });

            MSTResult prim = PrimAlgorithm.run(graph);
            MSTResult kruskal = KruskalAlgorithm.run(graph);

            JsonObject result = new JsonObject();
            result.addProperty("graph_id", id);

            JsonObject stats = new JsonObject();
            stats.addProperty("vertices", graph.getVerticesCount());
            stats.addProperty("edges", graph.getEdgesCount());
            result.add("input_stats", stats);

            result.add("prim", toJson(gson, prim));
            result.add("kruskal", toJson(gson, kruskal));
            results.add(result);
        }

        JsonObject output = new JsonObject();
        output.add("results", results);

        try (FileWriter fw = new FileWriter("data/output.json")) {
            gson.toJson(output, fw);
        }

        System.out.println("✅ Output written to data/output.json");
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
