package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;
import util.datastructure.MyQueue;

import java.util.Queue;

/**
 * 基于队列的拓扑排序。实现一种拓扑排序，使用顶点索引的数组来保存每个顶点的入度。
 * 遍历一遍所有边并使用 {@link Degrees} 类来初始化数组以及一条含有所有顶点的队列。
 * 然后，重复一下操作直到起点队列为空：
 * - 从队列中删去一个顶点并将其标记
 * - 遍历由被删除顶点指出的所有边，将所有被指向的顶点的入度减一
 * - 如果顶点的入度变为 0，将它插入顶点队列
 */
public class E30_QueueBaseTopological {


}


class QueueTopological {

    private Queue<Integer> top;


    public QueueTopological(Digraph g) {
        top = new MyQueue<>();
        Degrees degrees = new Degrees(g);
        int[] degree = new int[g.vertexNum()];
        // 包含了入度为 0 的顶点
        Queue<Integer> sources = new MyQueue<>();
        for (int v = 0; v < g.vertexNum(); v++) {
            if (degrees.inDegree(v) == 0)
                sources.offer(v);
            degree[v] = degrees.inDegree(v);
        }

        while (!sources.isEmpty()) {
            Integer v = sources.poll();
            top.offer(v);
            for (Integer w : g.adj(v)) {
                if (--degree[w] == 0)
                    sources.offer(w);
            }
        }
        if (top.size() != g.vertexNum()) {
            top.clear();
        }
    }


    public boolean isDAG() {
        return !top.isEmpty();
    }

    public Iterable<Integer> order() {
        return top;
    }
}
