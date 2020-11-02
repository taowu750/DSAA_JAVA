package algs4_graph.sec2_digraph.src;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import util.algs.In;

import java.util.LinkedList;
import java.util.List;

/**
 * 简单有向图实现。
 */
@SuppressWarnings("unchecked")
public class Digraph implements SimpleGraph {

    private final int vertexNum;
    private int edgeNum;
    private List<Integer>[] adjs;


    public Digraph(int vertexNum) {
        this.vertexNum = vertexNum;
        adjs = new LinkedList[vertexNum];

        for (int i = 0; i < vertexNum; i++) {
            adjs[i] = new LinkedList<>();
        }
    }

    public Digraph(In in) {
        this(in.readInt());
        int edgeNum = in.readInt();
        for (int i = 0; i < edgeNum; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }


    @Override
    public int vertexNum() {
        return vertexNum;
    }

    @Override
    public int edgeNum() {
        return edgeNum;
    }

    @Override
    public void addEdge(int v, int w) {
        adjs[v].add(0, w);
        edgeNum++;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        return adjs[v].contains(w);
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adjs[v];
    }

    public Digraph reverse() {
        Digraph r = new Digraph(vertexNum);
        for (int v = 0; v < vertexNum; v++) {
            for (Integer w : adjs[v]) {
                r.addEdge(w, v);
            }
        }

        return r;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Digraph {\n\t" + vertexNum + " vertices, " + edgeNum + ", edges\n");
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
