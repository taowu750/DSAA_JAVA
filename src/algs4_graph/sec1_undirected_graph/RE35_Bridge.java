package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: 不行
/**
 * 在一幅连通图中，如果一条边删除后图变成了两个联通分量，这条边就成为桥。
 * 没有桥的图成为边连通图。判断一个图是否是边连通图。
 */
public class RE35_Bridge {

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

class Bridge {

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
