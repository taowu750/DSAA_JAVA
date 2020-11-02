package algs4_graph.sec2_digraph.src;

import util.datastructure.MyArrayList;

import java.util.List;

/**
 * <p>
 * 练习 E7：顶点的出度和入度。
 * </p>
 * <p>
 * 出度为 0 的顶点叫做终点，入度为 0 的顶点叫做起点。一幅允许出现自环
 * 且每个顶点的出度均为 1 的有向图叫做映射（从0到V-1之间的整数到它们自身的函数）。
 * </p>
 */
public class Degrees {

    private int[] inDegrees;
    private int[] outDegrees;

    private List<Integer> sources;
    private List<Integer> sinks;

    private boolean isMap;


    public Degrees(Digraph g) {
        inDegrees = new int[g.vertexNum()];
        outDegrees = new int[g.vertexNum()];
        sources = new MyArrayList<>();
        sinks = new MyArrayList<>();

        for (int v = 0; v < g.vertexNum(); v++) {
            for (Integer w : g.adj(v)) {
                outDegrees[v]++;
                inDegrees[w]++;
            }
            if (outDegrees[v] == 0)
                sinks.add(v);
        }
        if (sinks.size() == g.vertexNum())
            isMap = true;
        for (int v = 0; v < g.vertexNum(); v++) {
            if (inDegrees[v] == 0)
                sources.add(v);
        }
    }


    public int inDegree(int v) {
        return inDegrees[v];
    }

    public int outDegree(int v) {
        return outDegrees[v];
    }

    public Iterable<Integer> sources() {
        return sources;
    }

    public Iterable<Integer> sinks() {
        return sinks;
    }

    public boolean isMap() {
        return isMap;
    }
}
