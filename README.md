# Analytical Report: Optimization of a City Transportation Network (MST)
Kassymgali Bakytzhan
SE-2421

## 1. Introduction

This project optimizes a city transportation network using **Minimum Spanning Tree (MST)** algorithms. Each district is represented as a vertex, and each possible road is an edge weighted by construction cost.

**Input Data:**  
* Provided in [`assign_3_input_reset.json`](./data/assign_3_input_reset.json)  
* Multiple datasets were used to evaluate correctness, performance, and efficiency.

---

## 2. Algorithm Implementations

### 2.1 Prim’s Algorithm
* Builds MST starting from a single vertex.  
* Adds the **minimum-weight edge** connecting MST to a new vertex.  
* **Data Structures:** `PriorityQueue`, `HashSet` for visited nodes, adjacency list.  
* **Operations counted:** edge comparisons and insertions.  
* **Time Complexity:** Dense: `O(V²)`, Sparse: `O(E log V)`  
* **Reference:** Cormen et al., *Introduction to Algorithms* (2009), [Prim's Algorithm](https://en.wikipedia.org/wiki/Prim%27s_algorithm)

### 2.2 Kruskal’s Algorithm
* Sorts all edges by weight and adds edges if they do **not form a cycle**.  
* **Data Structures:** Disjoint Set Union (DSU), sorted edge list.  
* **Operations counted:** DSU `find` and `union`, edge comparisons.  
* **Time Complexity:** `O(E log E)`  
* **Reference:** Cormen et al., *Introduction to Algorithms* (2009), [Kruskal's Algorithm](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)

---

## 3. Results Summary

| Dataset | Algorithm | Total Cost | Execution Time (ms) | Operation Count | Disconnected |
| ------- | --------- | ---------- | ------------------ | --------------- | ------------ |
| graph1  | Prim      | 10         | 0.5                | 8               | false        |
| graph1  | Kruskal   | 10         | 0.3                | 7               | false        |
| graph2  | Prim      | 15         | 1.2                | 12              | true         |
| graph2  | Kruskal   | 15         | 1.0                | 11              | true         |

**Observations:**  
* Both algorithms produce identical MST total cost.  
* Disconnected graphs handled correctly.  
* Prim slightly slower on sparse graphs; Kruskal faster when few edges.  
* Repeated runs produce consistent results, confirming **reproducibility**.

---

## 4. Graphical Analysis

**Execution Time Comparison**  

![Execution Time](./execution_time_comparison.png)  

**Operations Count Comparison**  

![Operations Count](./operations_count_comparison.png)  

* Kruskal scales predictably with edge count.  
* Prim performs more operations in sparse graphs due to repeated queue insertions.

---

## 5. Algorithm Comparison

| Feature               | Prim’s Algorithm                  | Kruskal’s Algorithm                    |
| --------------------- | --------------------------------- | -------------------------------------- |
| Approach              | Greedy, grows MST from one vertex | Greedy, sorts edges and connects trees |
| Data Structures       | Priority Queue, adjacency list    | Disjoint Set Union (DSU)               |
| Complexity (Dense)    | O(V²) or O(E log V)               | O(E log E)                             |
| Complexity (Sparse)   | O(E log V)                        | O(E log E)                             |
| Practical Performance | Slower on sparse graphs           | Faster on sparse graphs                |
| Implementation Ease   | Slightly more complex with PQ     | Simple with DSU                        |

**Insights:**  
* Dense graphs → Prim preferred.  
* Sparse graphs → Kruskal preferred.  
* Efficiency depends on **graph density** and **data structures**.

---

## 6. Conclusions

1. Both algorithms correctly compute MST with minimal cost.  
2. Choice depends on **graph density**: Dense → Prim, Sparse → Kruskal.  
3. Prim slightly more complex to implement (priority queue).  
4. Graphical and CSV analysis confirm theoretical expectations.  

---

## 7. References

1. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). *Introduction to Algorithms*, 3rd Edition. MIT Press.  
2. Wikipedia contributors. (2025). *Prim's algorithm*. [Link](https://en.wikipedia.org/wiki/Prim%27s_algorithm)  
3. Wikipedia contributors. (2025). *Kruskal's algorithm*. [Link](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)

---

## 8. GitHub / Project Integration

* Input JSON: [`assign_3_input_reset.json`](./data/assign_3_input_reset.json)  
* Output CSV: [`mst_summary.csv`](./mst_summary.csv)  
* Graphs: `execution_time_comparison.png`, `operations_count_comparison.png`
