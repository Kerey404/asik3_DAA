import json
import pandas as pd
import matplotlib.pyplot as plt
import os


INPUT_FILE = "data/output.json"
CSV_FILE = "data/mst_summary.csv"
IMG_TIME = "data/execution_time_comparison.png"
IMG_OPS = "data/operations_count_comparison.png"


if not os.path.exists(INPUT_FILE):
    raise FileNotFoundError(f"❌ File not found: {INPUT_FILE}")


with open(INPUT_FILE, "r", encoding="utf-8") as f:
    data = json.load(f)

rows = []
for item in data.get("results", []):
    gid = item.get("graph_id")
    stats = item.get("input_stats", {})
    prim = item.get("prim", {})
    kruskal = item.get("kruskal", {})

    rows.append({
        "graph_id": gid,
        "vertices": stats.get("vertices"),
        "edges": stats.get("edges"),
        "prim_time_ms": prim.get("execution_time_ms"),
        "kruskal_time_ms": kruskal.get("execution_time_ms"),
        "prim_ops": prim.get("operations_count"),
        "kruskal_ops": kruskal.get("operations_count"),
        "prim_cost": prim.get("total_cost"),
        "kruskal_cost": kruskal.get("total_cost"),
        "prim_disconnected": prim.get("disconnected"),
        "kruskal_disconnected": kruskal.get("disconnected")
    })


df = pd.DataFrame(rows)
df = df.sort_values("graph_id").reset_index(drop=True)

# === 5. Сохраняем CSV ===
os.makedirs("data", exist_ok=True)
df.to_csv(CSV_FILE, index=False, encoding="utf-8")
print(f"✅ CSV saved to {CSV_FILE}")

# === 6. Печатаем структуру ===
print("\n📊 Data Structure:")
print(df.info())
print("\n📈 First 10 rows:")
print(df.head(10))


plt.figure(figsize=(10, 5))
plt.plot(df["graph_id"], df["prim_time_ms"], label="Prim (ms)", marker="o", color="green")
plt.plot(df["graph_id"], df["kruskal_time_ms"], label="Kruskal (ms)", marker="o", color="blue")
plt.title("Execution Time: Prim vs Kruskal")
plt.xlabel("Graph ID")
plt.ylabel("Time (ms)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(IMG_TIME)
print(f"📊 Saved: {IMG_TIME}")


plt.figure(figsize=(10, 5))
plt.plot(df["graph_id"], df["prim_ops"], label="Prim Ops", marker="o", color="green")
plt.plot(df["graph_id"], df["kruskal_ops"], label="Kruskal Ops", marker="o", color="blue")
plt.title("Operations Count: Prim vs Kruskal")
plt.xlabel("Graph ID")
plt.ylabel("Operations")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(IMG_OPS)
print(f"📊 Saved: {IMG_OPS}")

print("\n✅ Analysis complete!")
print(f"Files created:\n- {CSV_FILE}\n- {IMG_TIME}\n- {IMG_OPS}")
