package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.DFSOrder;
import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphCycle;
import org.junit.jupiter.api.Test;
import util.datastructure.MyQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Exam {

    // TODO: 不行
    /**
     * ### 20. 欧拉环是图中经过每条边一次且仅一次的环。找出有向图中的欧拉环或者说明它不存在。
     */
    public static class Euler {

        private Digraph g;
        private Degrees degrees;
        private boolean hasEuler;
        private Iterable<Integer> euler = new ArrayList<>(1);

        public Euler(Digraph g) {
            this.g = g;
            degrees = new Degrees(g);

            hasEuler = true;
            for (int v = 0; v < g.vertexNum(); v++) {
                if (degrees.inDegree(v) != degrees.outDegree(v)) {
                    hasEuler = false;
                    break;
                }
            }
            if (hasEuler)
                euler = new DFSOrder(g).pre();
        }

        public boolean hasEuler() {
            return hasEuler;
        }

        public Iterable<Integer> euler() {
            return euler;
        }
    }

    // TODO: 接近
    /**
     * ### 21. 有向无环图中的LCA（最近共同祖先）。给定一幅有向无环图和两个顶点v和w，找出v和w的LCA。
     */
    public class LCA {

        private Digraph g;
        private Digraph inv;
        private Queue<Integer> queue;
        private boolean[] vMarked, wMarked, hMarked;
        private int[] vDist, wDist, hDist;

        public LCA(Digraph g) {
            this.g = g;
            inv = g.reverse();
            queue = new MyQueue<>();
            vMarked = new boolean[g.vertexNum()];
            wMarked = new boolean[g.vertexNum()];
            hMarked = new boolean[g.vertexNum()];
            vDist = new int[g.vertexNum()];
            wDist = new int[g.vertexNum()];
            hDist = new int[g.vertexNum()];

            if (new DigraphCycle(g).hasCycle())
                throw new IllegalArgumentException("参数有向图中包含环");
        }

        public int lca(int v, int w) {
            Arrays.fill(vMarked, false);
            Arrays.fill(wMarked, false);
            Arrays.fill(vDist, -1);
            Arrays.fill(wDist, -1);
            vDist[v] = 0;
            wDist[w] = 0;

            bfs(inv, v, vMarked, vDist);
            bfs(inv, w, wMarked, wDist);

            int lca = -1, maxHeight = Integer.MIN_VALUE;
            for (int n = 0; n < g.vertexNum(); n++) {
                // 公共父顶点中，最大高度（到入度为0结点的最小路径长度）的父顶点是LCA
                if (vMarked[n] && wMarked[n]) {
                    int h = height(inv, n);
                    if (maxHeight < h) {
                        lca = n;
                        maxHeight = h;
                    }
                }
            }

            return lca;
        }

        private void bfs(Digraph inv, int v, boolean[] marked, int[] dist) {
            queue.clear();

            queue.offer(v);
            marked[v] = true;

            while (!queue.isEmpty()) {
                v = queue.poll();
                for (int w: inv.adj(v)) {
                    if (!marked[w]) {
                        marked[w] = true;
                        dist[w] = dist[v] + 1;
                        queue.offer(w);
                    }
                }
            }
        }

        private int height(Digraph inv, int v) {
            queue.clear();
            Arrays.fill(hMarked, false);
            Arrays.fill(hDist, -1);
            int h = Integer.MAX_VALUE;

            hMarked[v] = true;
            hDist[v] = 0;
            queue.offer(v);

            while (!queue.isEmpty()) {
                v = queue.poll();
                boolean hasEdge = false;
                for (int w: inv.adj(v)) {
                    hasEdge = true;
                    if (!hMarked[w]) {
                        hMarked[w] = true;
                        hDist[w] = hDist[v] + 1;
                        queue.offer(w);
                    }
                }

                // 发现起点
                if (!hasEdge && h > hDist[v]) {
                    h = hDist[v];
                }
            }

            return h;
        }
    }

    @Test
    public void test_LCA() {
        // 这是一个层次的图，从0-12，(0,1,2)、(3,4)、(5,6,7,8)、(9,10)、(11,12)分别是不同的层
        Digraph g = new Digraph(13);
        g.addEdge(0, 3);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(4, 8);
        g.addEdge(6, 9);
        g.addEdge(7, 9);
        g.addEdge(8, 7);
        g.addEdge(8, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(10, 12);

        LCA lca = new LCA(g);
        assertEquals(lca.lca(5, 10), 1);
        assertEquals(lca.lca(3, 1), 1);
        assertEquals(lca.lca(3, 4), 1);
        assertEquals(lca.lca(0, 2), -1);
        assertEquals(lca.lca(6, 12), 6);
        assertEquals(lca.lca(6, 12), 6);
        assertEquals(lca.lca(11, 10), 8);
        assertEquals(lca.lca(5, 9), 3);
    }
}
