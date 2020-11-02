package algs4_graph.sec4_shortest_path.src;

import util.algs.In;
import util.io.AlgsDataIO;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权有向图。
 */
@SuppressWarnings("unchecked")
public class WeightedDigraph {

    private int vertexNum;
    private int edgeNum;
    private List<DirectedEdge>[] adjs;


    public WeightedDigraph(int vertexNum) {
        this.vertexNum = vertexNum;
        adjs = new List[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            adjs[i] = new ArrayList<>();
        }
    }

    public WeightedDigraph(In in) {
        this(in.readInt());

        int edgeNum = in.readInt();
        for (int i = 0; i < edgeNum; i++) {
            addEdge(new DirectedEdge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }


    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph(AlgsDataIO.openTinyEWD());
        System.out.println(g);
    }


    public int vertexNum() {
        return vertexNum;
    }

    public int edgeNum() {
        return edgeNum;
    }

    public void addEdge(DirectedEdge e) {
        adjs[e.from()].add(0, e);
        edgeNum++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adjs[v];
    }

    public Iterable<DirectedEdge> edges() {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int v = 0; v < vertexNum; v++) {
            edges.addAll(adjs[v]);
        }

        return edges;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("WeightedDigraph {\n\t" + vertexNum + " vertices, " + edgeNum + " edges\n");
        for (int v = 0; v < vertexNum; v++) {
            for (DirectedEdge e : adjs[v]) {
                s.append('\t').append(e).append('\n');
            }
        }
        s.append('}');

        return s.toString();
    }
}
