package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * 在一副<strong>连通图</strong>中，如果一条边被删除后图会被分成两个独立的连通分量，
 * 这条边就被称为桥。没有桥的图称为边连通图。
 * </p>
 * <p>
 * 开发一种基于深度优先搜索算法的数据类型，判断一个图是否是边连通图。
 * </p>
 * <p>
 * 桥是这样一种构造：除了桥以外，桥两端的顶点之间没有其他任何路径。也可以说，
 * 桥两端的顶点是无环的。
 * </p>
 */
public class E35_Bridge {

    /*
    构造一个含有桥的连通图
     */
    public static UndirectedGraph newBridgeG() {
        UndirectedGraph g = new UndirectedGraph(15);
        // 6结点和7结点之间有桥
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 10);
        g.addEdge(8, 11);
        g.addEdge(9, 10);
        g.addEdge(9, 12);
        g.addEdge(9, 14);
        g.addEdge(10, 14);
        g.addEdge(11, 12);
        g.addEdge(12, 13);
        g.addEdge(13, 14);

        return g;
    }

    /*
    构造一个没有桥的图。
     */
    public static UndirectedGraph newEdgeG() {
        UndirectedGraph g = new UndirectedGraph(15);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 10);
        g.addEdge(8, 11);
        g.addEdge(9, 10);
        g.addEdge(9, 12);
        g.addEdge(9, 14);
        g.addEdge(10, 14);
        g.addEdge(11, 12);
        g.addEdge(12, 13);
        g.addEdge(13, 14);

        return g;
    }

    @Test
    public void testBridge() throws Exception {
        Bridge b = new Bridge(newBridgeG());
        assertFalse(b.isEdgeConnected());
        assertEquals(b.bridges(), 1);

        assertTrue(new Bridge(newEdgeG()).isEdgeConnected());
    }
}


/*
Tarjan 算法求桥。
从某个顶点开始进行 DFS，并进行标号。如果某个顶点的某条边的 DFS 中有某个孩子
有边指向这个顶点的祖先，那么这条边不为桥，否则为桥。
 */
class Bridge {

    private int bridges;
    private int count;

    private int[] order;
    private int[] anchor;


    /*
    g 是连通图。
     */
    public Bridge(UndirectedGraph g) {
        order = new int[g.vertexNum()];
        anchor = new int[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            order[v] = -1;
            anchor[v] = -1;
        }

        for (int v = 0; v < g.vertexNum(); v++) {
            if (order[v] == -1)
                dfs(g, v, v);
        }
    }


    public boolean isEdgeConnected() {
        return bridges == 0;
    }

    public int bridges() {
        return bridges;
    }


    private void dfs(UndirectedGraph g, int v, int parent) {
        // order[i] 表示顶点 i 访问的顺序，访问顺序靠前的就是后面的祖先
        order[v] = count++;
        // anchor[i] 表示顶点 i 和它的孩子中指向的最早的祖先
        // 这里设置为自己，如果后序没有更新的话，表示没有指向祖先的路径，也就是有桥
        anchor[v] = order[v];

        for (Integer w : g.adj(v)) {
            if (order[w] == -1) {
                dfs(g, w, v);
                // 此时顶点 w dfs过程已经结束

                // 更新顶点 v 的祖先为最早的祖先
                anchor[v] = Math.min(anchor[v], anchor[w]);
                // w 最早祖先等于 w，表示没有指向祖先的路径，此时边 v-w 为桥
                if (anchor[w] == order[w])
                    bridges++;
            }
            // w != parent，表示顶点 v 遇到了它的一个祖先
            else if (w != parent) {
                // 标号越小，表示这个祖先越早
                anchor[v] = Math.min(anchor[v], order[w]);
            }
        }
    }
}
