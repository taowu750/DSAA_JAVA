package algs4_graph.sec1_undirected_graph.src;

import util.algs.In;
import util.io.AlgsDataIO;

import java.util.LinkedList;
import java.util.List;

/**
 * 简单无向图实现。
 */
@SuppressWarnings("unchecked")
public class UndirectedGraph implements SimpleGraph {

    private final int vertexNum;
    private int edgeNum;
    // 邻接表
    private List<Integer>[] adjs;


    public UndirectedGraph(int vertexNum) {
        this.vertexNum = vertexNum;
        adjs = (List<Integer>[]) new List[vertexNum];

        for (int i = 0; i < vertexNum; i++) {
            adjs[i] = new LinkedList<>();
        }
    }

    public UndirectedGraph(In in) {
        this(in.readInt());
        int edgeNum = in.readInt();
        for (int i = 0; i < edgeNum; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }


    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyG());
        System.out.println(g);
    }


    public int vertexNum() {
        return vertexNum;
    }

    public int edgeNum() {
        return edgeNum;
    }

    public void addEdge(int v, int w) {
        // 边的不同插入顺序会极大地影响深度和广度优先遍历的情况
        adjs[v].add(0, w);
        adjs[w].add(0, v);
        edgeNum++;
    }

    public boolean hasEdge(int v, int w) {
        return adjs[v].contains(w);
    }

    public Iterable<Integer> adj(int v) {
        return adjs[v];
    }

    /**
     * 节点 v 的度数。
     *
     * @param v
     * @return
     */
    public int degree(int v) {
        return adjs[v].size();
    }

    /**
     * 图的最大度数
     *
     * @return
     */
    public int maxDegree() {
        int max = 0;
        for (List<Integer> adj : adjs) {
            if (adj.size() > max)
                max = adj.size();
        }

        return max;
    }

    /**
     * 图的平均度数
     *
     * @return
     */
    public double aveDegree() {
        return 2 * edgeNum / (vertexNum * 1.);
    }

    /**
     * 图中自环的数量
     *
     * @return
     */
    public int selfLoopNum() {
        int count = 0;
        for (int v = 0; v < vertexNum; v++) {
            for (Integer w : adjs[v]) {
                if (v == w)
                    count++;
            }
        }

        return count / 2; // 每条边都被记过两次
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("UndirectedGraph {\n\t" + vertexNum + " vertices, " + edgeNum + ", edges\n");
        for (int v = 0; v < vertexNum; v++) {
            s.append('\t').append(v).append(": ");
            for (Integer w : adjs[v]) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        s.append('}');

        return s.toString();
    }
}
