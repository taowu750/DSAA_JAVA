package algs4_graph.sec3_mst.src;

import util.algs.In;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 加权无向图。
 */
@SuppressWarnings("unchecked")
public class WeightedGraph implements SimpleWeighedGraph {

    private int vertexNum;
    private int edgeNum;
    private List<Edge>[] adjs;


    public WeightedGraph(int vertexNum) {
        this.vertexNum = vertexNum;
        edgeNum = 0;
        adjs = new List[vertexNum];

        for (int i = 0; i < vertexNum; i++) {
            adjs[i] = new LinkedList<>();
        }
    }

    public WeightedGraph(In in) {
        this(in.readInt());

        int edgeNum = in.readInt();
        for (int i = 0; i < edgeNum; i++) {
            addEdge(new Edge(in.readInt(), in.readInt(), in.readDouble()));
        }
    }


    public int vertexNum() {
        return vertexNum;
    }

    public int edgeNum() {
        return edgeNum;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);

        adjs[v].add(0, e);
        adjs[w].add(0, e);
        edgeNum++;
    }

    public void deleteEdge(Edge edge) {
        int v = edge.either(), w = edge.other(v);

        adjs[v].remove(edge);
        adjs[w].remove(edge);
        edgeNum--;
    }

    public Iterable<Edge> adj(int v) {
        return adjs[v];
    }

    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for (int v = 0; v < vertexNum; v++) {
            for (Edge edge : adjs[v]) {
                if (edge.other(v) > v)
                    edges.add(edge);
            }
        }

        return edges;
    }

    public void clearEdges() {
        for (List<Edge> adj : adjs) {
            adj.clear();
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("WeightedGraph {\n\t" + vertexNum + " vertices, " + edgeNum + " edges\n");
        for (int v = 0; v < vertexNum; v++) {
            s.append('\t').append(v).append(": ");
            for (Edge e : adjs[v]) {
                s.append(e.other(v)).append(" ");
            }
            s.append("\n");
        }
        s.append('}');

        return s.toString();
    }
}
