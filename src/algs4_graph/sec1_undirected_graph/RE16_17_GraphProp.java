package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import org.junit.jupiter.api.Test;
import util.datastructure.MyQueue;
import util.io.AlgsDataIO;

import java.util.Arrays;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 求图的离心率、直径、半径、中点和周长
 */
public class RE16_17_GraphProp {

    @Test
    public void test_GraphProperties() {
        SimpleGraph graph = new UndirectedGraph(AlgsDataIO.openTinyCG());
        GraphProperties p = new GraphProperties(graph);

        assertEquals(p.diameter(), 2);
        assertEquals(p.radius(), 2);
        assertEquals(p.girth(), 3);
    }
}

class GraphProperties {

    private SimpleGraph g;
    private boolean[] marked;
    private int[] dist;
    private int[] ecc;
    private int d, r, c;
    private int[] parent;
    private int girth = Integer.MAX_VALUE;


    public GraphProperties(SimpleGraph g) {
        this.g = g;
        marked = new boolean[g.vertexNum()];
        dist = new int[g.vertexNum()];
        ecc = new int[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            bfs(v);
            ecc[v] = Arrays.stream(dist).max().orElse(0);
        }

        r = ecc[0];
        d = ecc[0];
        c = 0;
        for (int v = 1; v < g.vertexNum(); v++) {
            if (ecc[v] < r) {
                r = ecc[v];
                c = v;
            }
            if (ecc[v] > d)
                d = ecc[v];
        }

        Arrays.fill(marked, false);
        Arrays.fill(dist, 0);
        parent = new int[g.vertexNum()];
        Arrays.fill(parent, -1);
        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v]) {
                circle_check(v);
            }
        }
    }


    /**
     * 顶点v的离心率，即离它最远的顶点的最短距离
     *
     * @param v
     * @return
     */
    public int eccentricity(int v) {
        return ecc[v];
    }

    /**
     * 图的直径，即所有顶点的最大离心率
     *
     * @return
     */
    public int diameter() {
        return d;
    }

    /**
     * 图的半径，即所有顶点的最小离心率
     *
     * @return
     */
    public int radius() {
        return r;
    }

    /**
     * 图的中点，离心率和半径相等的某个点
     *
     * @return
     */
    public int center() {
        return c;
    }

    /**
     * 图的周长，即图中最短环的长度。如果是无环图，周长为无穷大
     *
     * @return
     */
    public int girth() {
        return girth;
    }


    private void bfs(int vertex) {
        Arrays.fill(marked, false);
        Arrays.fill(dist, 0);
        Queue<Integer> queue = new MyQueue<>(marked.length);

        marked[vertex] = true;
        queue.offer(vertex);
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int w: g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    dist[w] = dist[v] + 1;
                    queue.offer(w);
                }
            }
        }
    }

    private void circle_check(int vertex) {
        Queue<Integer> queue = new MyQueue<>(marked.length);

        marked[vertex] = true;
        queue.offer(vertex);
        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int w: g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    dist[w] = dist[v] + 1;
                    parent[w] = v;
                    queue.offer(w);
                } else if (parent[v] != -1 && w != parent[v]) {
                    int cycleLen = dist[w] + dist[v] + 1;
                    if (cycleLen < girth)
                        girth = cycleLen;
                }
            }
        }
    }
}
