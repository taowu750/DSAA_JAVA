package algs4_graph.sec3_mst;

import algs1_fundamentals.sec5_union_find.src.IUnionFind;
import algs1_fundamentals.sec5_union_find.src.UnionFindWeighted;
import algs4_graph.sec3_mst.src.Edge;
import algs4_graph.sec3_mst.src.SimpleWeighedGraph;
import algs4_graph.sec3_mst.src.WeightedGraph;
import algs4_graph.sec3_mst.src.WeightedGraphCycle;
import util.datastructure.MyPriorityQueue;

import java.util.*;
import java.util.stream.StreamSupport;

public class Exam {

    /**
     * ### 26. 关键边指的是图中最小生成树的某一条边，如果删除它，那么新图中最小生成树
     * 的总权重会大于原来的权重。时间复杂度要求O(ElogE)
     * 注意，这个问题中边的权重并不一定各不相同，否则最小生成树中所有的边就都会是关键边。
     *
     * @param g
     * @return
     */
    public static Iterable<Edge> keyEdges(SimpleWeighedGraph g) {
        /*
         边e是关键的当且仅当它是包含所有权重小于等于它的边构成的子图中的桥（这个子图包含最小生成树的一部分）。

        证明：
        必要性：如果一条边e是关键的，那么它是包含所有权重小于等于它的边构成的子图中的桥。
        假设边e不是这样子图中的桥，则有另一条边f连接子图中与e相同的分量，并且它的权重小于或等于e。
        在这种情况下，边f和边e属于同一个横切边集合，边e可以被边f代替，并且MST的权重不会增加。
        然而，由于e是关键的并且不能被权重小于或等于它的边代替，它必须是子图中的一个桥。

        充分性：如果一条边e是包含所有权重小于等于它的边构成的子图中的桥，则e是关键的。
        假设e不是关键的，那么必须有另一个边可以在MST中代替它，并且不会导致MST的权重增加。
        然而，如果这条边存在，它将是包含所有权重小于等于e的边构成的子图中的一部分。
        因为它可以在MST中代替e，它们属于同一个横切边集合，所以它还将连接由边e连接的分量C1和C2，。
        但是，e是一个桥，所以不存在这种情况。因此，边e是关键的。
         */

        Queue<Edge> criticalEdges = new ArrayDeque<>();

        /*
        修改后的Kruskal算法
         */
        Queue<Edge> mst = new ArrayDeque<>();
        MyPriorityQueue<Edge> pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN, g.vertexNum());
        StreamSupport.stream(g.edges().spliterator(), false).forEach(pq::offer);

        IUnionFind uf = new UnionFindWeighted(g.vertexNum());
        // 为了利用子图，对无向加权图进行修改使其支持删除边
        WeightedGraph subGraph = new WeightedGraph(uf.count());

        // Kruskal 算法遍历的过程中会从最小权重边依次开始，这样构建子图就非常方便
        while (!pq.isEmpty() && mst.size() < g.vertexNum() - 1) {
            Edge edge = pq.poll();

            int v = edge.either(), w = edge.other(v);
            if (uf.connected(v, w))
                continue;

            double currentWeight = edge.weight();
            HashSet<Edge> equalWeightEdges = new HashSet<>();
            equalWeightEdges.add(edge);

            // 找出所有和当前边权重相同的边
            while (!pq.isEmpty() && pq.peek().weight() == currentWeight) {
                equalWeightEdges.add(pq.poll());
            }

            // 如果当前边权重唯一，则没有环，是关键边
            if (equalWeightEdges.size() == 1) {
                criticalEdges.add(edge);
                uf.union(v, w);
                mst.add(edge);
                continue;
            }

            List<Edge> edgesToAddToComponentsSubGraph = new ArrayList<>();
            // 对uf分量子图中的边与原始图进行映射
            HashMap<Edge, Edge> subGraphToGraphEdgeMap = new HashMap<>(equalWeightEdges.size());
            HashSet<Integer> verticesInSubGraph = new HashSet<>();

            // 为当前uf分量生成子图
            for (Edge edgeInCurrentBlock : equalWeightEdges) {
                v = edgeInCurrentBlock.either();
                w = edgeInCurrentBlock.other();

                int c1 = uf.find(v);
                int c2 = uf.find(w);

                Edge subGraphEdge = new Edge(c1, c2, currentWeight);
                edgesToAddToComponentsSubGraph.add(subGraphEdge);

                subGraphToGraphEdgeMap.put(subGraphEdge, edgeInCurrentBlock);
                verticesInSubGraph.add(c1);
                verticesInSubGraph.add(c2);
            }
            edgesToAddToComponentsSubGraph.forEach(subGraph::addEdge);

            HashSet<Edge> nonCriticalEdges = new HashSet<>();
            // 只遍历必要的顶点，避免 O(E * V) 的时间复杂度
            WeightedGraphCycle cycle = new WeightedGraphCycle(subGraph, verticesInSubGraph);
            if (cycle.hasCycle()) {
                cycle.cycle().forEach(e -> nonCriticalEdges.add(subGraphToGraphEdgeMap.get(e)));
            }

            subGraph.clearEdges();

            for (Edge edgeInCurrentBlock : equalWeightEdges) {
                // 所有不在环中的边都是关键边
                if (!nonCriticalEdges.contains(edgeInCurrentBlock))
                    criticalEdges.add(edgeInCurrentBlock);

                v = edgeInCurrentBlock.either();
                w = edgeInCurrentBlock.other();

                if (!uf.connected(v, w)) {
                    uf.union(v, w);
                    mst.add(edge);
                }
            }
        }

        return criticalEdges;
    }
}
