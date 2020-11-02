package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import org.junit.jupiter.api.Test;
import util.datastructure.MyQueue;
import util.io.AlgsDataIO;

import java.util.Arrays;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class Exam {

    /**
     * 判断图中是否有环
     */
    public static class Cycle {

        private SimpleGraph g;
        private boolean[] marked;
        private boolean hasCycle;

        public boolean hasCycle(SimpleGraph g) {
            this.g = g;
            marked = new boolean[g.vertexNum()];
            for (int v = 0; v < g.vertexNum(); v++) {
                if (!marked[v])
                    dfs(v, v);
            }

            return hasCycle;
        }

        private void dfs(int v, int parent) {
            marked[v] = true;
            for (int w: g.adj(v)) {
                if (!marked[w]) {
                    dfs(w, v);
                } else if (w != parent) {
                    hasCycle = true;
                    return;
                }
            }
        }
    }

    /**
     * 判断给定图是否是二分图
     */
    public static class BipartiteGraph {

        private SimpleGraph g;
        private Boolean[] color;
        private boolean isBipartite = true;


        public BipartiteGraph(SimpleGraph g) {
            this.g = g;
            color = new Boolean[g.vertexNum()];

            for (int v = 0; v < g.vertexNum(); v++) {
                if (color[v] == null) {
                    color[v] = true;
                    dfs(v);
                }
            }
        }


        public boolean isBipartite() {
            return isBipartite;
        }


        private void dfs(int v) {
            for (int w: g.adj(v)) {
                if (color[w] == null) {
                    color[w] = !color[v];
                    dfs(w);
                } else if (color[w] == color[v]) {
                    isBipartite = false;
                    return;
                }
            }
        }
    }

    /**
     * ### 16 & 17. 求图的离心率、直径、半径、中点和周长
     */
    public static class GraphProperties {

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

    @Test
    public void test_GraphProperties() {
        SimpleGraph graph = new UndirectedGraph(AlgsDataIO.openTinyCG());
        GraphProperties p = new GraphProperties(graph);

        assertEquals(p.diameter(), 2);
        assertEquals(p.radius(), 2);
        assertEquals(p.girth(), 3);
    }

    // TODO: 不行
    /**
     * ### 35. 在一幅连通图中，如果一条边删除后图变成了两个联通分量，这条边就成为桥。
     * 没有桥的图成为边连通图。判断一个图是否是边连通图。
     */
    public static class Bridge {

        private SimpleGraph g;
        private boolean[] marked;
        private boolean[] inCircle;
        private int[] parent;
        private boolean hasEdge;


        public Bridge(SimpleGraph g) {
            this.g = g;
            marked = new boolean[g.vertexNum()];
            inCircle = new boolean[g.vertexNum()];
            parent = new int[g.vertexNum()];
            Arrays.fill(parent, -1);

            // 连通图
            dfs(0);
            for (boolean b : inCircle) {
                if (!b) {
                    hasEdge = true;
                    break;
                }
            }
        }

        public boolean hasEdge() {
            return hasEdge;
        }


        private void dfs(int v) {
            marked[v] = true;
            for (int w: g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    parent[w] = v;
                    dfs(w);
                } else if (parent[v] != -1 && parent[v] != w) {
                    for (int n = v; n != -1 && n != w; n = parent[n])
                        inCircle[n] = true;
                    inCircle[w] = true;
                }
            }
        }
    }

    @Test
    public void test_Bridge() {
        // 边连通图
        SimpleGraph g = new UndirectedGraph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        assertFalse(new Bridge(g).hasEdge());

        // 含有桥
        g = new UndirectedGraph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(2, 4);

        assertTrue(new Bridge(g).hasEdge());

        assertTrue(new Bridge(E35_Bridge.newBridgeG()).hasEdge());
        assertFalse(new Bridge(E35_Bridge.newBridgeG()).hasEdge());
    }
}
